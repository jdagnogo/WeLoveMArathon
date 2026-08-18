package com.jdagnogo.welovemarathon.events.presentation

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.ExpandableComponent
import com.jdagnogo.welovemarathon.common.ui.component.HtmlTextComponent
import com.jdagnogo.welovemarathon.common.ui.theme.EventUncategorizedColor
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.descriptionStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.ui.theme.tagStyle
import com.jdagnogo.welovemarathon.common.ui.theme.tagsTitleStyle
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.events.domain.Event
import com.jdagnogo.welovemarathon.events.domain.EventCategory
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private val CancelledRed = Color(0xFFD32F2F)
private val CancelledCardBackground = Color(0xFFFDECEC)
private val PastGray = Color(0xFF9E9E9E)
private val PastCardBackground = Color(0xFFF2F2F2)

@ExperimentalMaterialApi
@Composable
fun EventListItem(event: Event, day: LocalDate, onClick: () -> Unit) {
    val isCancelled = event.isCancelled
    // Cancelled takes visual priority; otherwise a past (already-ended) event
    // is grayed out to signal it's no longer relevant, per the reference. A
    // multi-day event keeps one shared startAt/endAt across every day it's
    // expanded into (see expandByDay), so event.isPast alone only goes true
    // once the *whole* range has ended - a day-in-the-past card would stay
    // ungrayed until the last day passes. Comparing the rendered day itself
    // against today catches that case too.
    val isPast = !isCancelled && (day.isBefore(LocalDate.now(EVENTS_ZONE)) || event.isPast)
    // A multi-day event is expanded into one card per day it spans (see
    // expandByDay), but "Happening now" only makes sense on the card for the
    // actual current day - otherwise every day of a week-long event would
    // claim to be happening now.
    val isToday = day == LocalDate.now(EVENTS_ZONE)
    val category = EventCategory.fromLabel(event.category)

    val badgeColor = when {
        isCancelled -> CancelledRed
        isPast -> PastGray
        else -> category?.color ?: EventUncategorizedColor
    }
    val cardBackground = when {
        isCancelled -> CancelledCardBackground
        isPast -> PastCardBackground
        else -> Color.White
    }
    val titleColor = if (isPast) Color.Gray else Color.Black
    val infoColor = if (isPast) Color.Gray else Secondary

    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 2.dp,
        backgroundColor = cardBackground,
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        ) {
            EventColorBadge(color = badgeColor, icon = category?.icon ?: Icons.Filled.CalendarToday)
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title.ifBlank { "Untitled event" },
                    style = tagStyle.copy(
                        fontSize = 16.sp,
                        color = titleColor,
                        textDecoration = if (isCancelled) TextDecoration.LineThrough else null,
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                category?.let {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                    Pill(text = "${it.emoji} ${it.label}", textColor = badgeColor, backgroundColor = badgeColor.copy(alpha = 0.12f))
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))

                // Time is the fact users scan for first - bold and in the app's
                // brand color so it visually outranks the rest of the card. The
                // status pill sits right next to it so both scannable facts
                // (when, and whether it's live right now) read together.
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    InfoRow(
                        icon = Icons.Filled.Schedule,
                        text = event.formattedTimeRange(),
                        textColor = infoColor,
                        fontWeight = FontWeight.SemiBold,
                    )
                    if (isCancelled) {
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                        Pill(text = "Cancelled", textColor = CancelledRed, backgroundColor = CancelledRed.copy(alpha = 0.12f))
                    } else if (isToday) {
                        relativeStartLabel(event.startAt, event.endAt)?.let { label ->
                            Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                            Pill(text = label, textColor = Color.White, backgroundColor = badgeColor)
                        }
                    }
                }

                if (event.location.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                    LocationRow(location = event.location, textColor = infoColor)
                }

                // Cancelled events show the "Cancelled" pill instead, and a
                // past event's description isn't relevant anymore either -
                // the original description is only shown for upcoming/live events.
                if (!isCancelled && !isPast && event.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    DescriptionSection(description = event.description)
                }
            }

            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.padding(start = MaterialTheme.spacing.extraSmall),
            )
        }
    }
}

@Composable
private fun EventColorBadge(color: Color, icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(color, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp),
        )
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    text: String,
    textColor: Color = Color.Gray,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    modifier: Modifier = Modifier,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textColor,
            modifier = Modifier.height(16.dp),
        )
        Text(
            text = text,
            style = descriptionStyle.copy(color = textColor, fontWeight = fontWeight, fontStyle = fontStyle),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = MaterialTheme.spacing.extraSmall),
        )
    }
}

/** Italic and tappable - opens the address in Google Maps so it reads
 * unmistakably as a link rather than plain metadata. */
@Composable
private fun LocationRow(location: String, textColor: Color) {
    val uriHandler = LocalUriHandler.current
    InfoRow(
        icon = Icons.Filled.LocationOn,
        text = location,
        textColor = textColor,
        fontStyle = FontStyle.Italic,
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = false),
        ) {
            redirectToLink(uriHandler, googleMapsSearchUrl(location))
        },
    )
}

/** Plain, muted, and collapsible - deliberately understated next to the
 * colored time/location rows so the card doesn't compete with itself.
 * Google Calendar descriptions come back as HTML (bold/italic/lists/links),
 * so this renders through HtmlTextComponent rather than a plain Text. */
@Composable
private fun DescriptionSection(description: String) {
    var isOverflowing by remember(description) { mutableStateOf(false) }
    ExpandableComponent(
        labelMore = "See more",
        labelLess = "See less",
        shouldShowExpandButton = isOverflowing,
    ) { isExpanded ->
        HtmlTextComponent(
            text = description,
            style = R.style.htmlTextDescription,
            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
            onOverflowDetected = { overflowing -> if (!isExpanded) isOverflowing = overflowing },
        )
    }
}

@Composable
private fun Pill(text: String, textColor: Color, backgroundColor: Color) {
    Text(
        text = text,
        style = tagsTitleStyle.copy(color = textColor, fontSize = 10.sp),
        maxLines = 1,
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 4.dp),
    )
}

private fun googleMapsSearchUrl(address: String): String =
    "https://www.google.com/maps/search/?api=1&query=" + Uri.encode(address)

fun Event.formattedTimeRange(zoneId: ZoneId = EVENTS_ZONE): String {
    if (isAllDay) return "All day"
    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH)
    val start = Instant.ofEpochMilli(startAt).atZone(zoneId).format(formatter)
    val end = Instant.ofEpochMilli(endAt).atZone(zoneId).format(formatter)
    return "$start - $end"
}
