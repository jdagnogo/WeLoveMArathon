package com.jdagnogo.welovemarathon.events.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.HtmlTextComponent
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.ContentBackgroundLight
import com.jdagnogo.welovemarathon.common.ui.theme.EventsColor
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.descriptionStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.ui.theme.tagsTitleStyle
import com.jdagnogo.welovemarathon.common.utils.redirectToLink
import com.jdagnogo.welovemarathon.events.domain.Event
import com.jdagnogo.welovemarathon.events.domain.EventCategory
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale

private val CancelledRed = Color(0xFFD32F2F)
private val CardBorder = BorderStroke(1.dp, Color(0xFFE0E0E0))

@Composable
fun EventDetailContent(
    item: Event,
    onCloseClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier
            .fillMaxSize()
            .background(ContentBackgroundLight),
    ) {
        TitleComponent(
            title = "Event Details",
            iconLeft = R.drawable.ic_back,
            onLeftIconClicked = onCloseClicked,
            iconRight = null,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = MaterialTheme.spacing.medium),
        ) {
            EventCategory.fromLabel(item.category)?.let { category ->
                Pill(
                    text = "${category.emoji} ${category.label}",
                    textColor = category.color,
                    backgroundColor = category.color.copy(alpha = 0.12f),
                    modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                )
            }

            Text(
                text = item.title.ifBlank { "Untitled event" },
                style = MaterialTheme.typography.h3.copy(color = Color.Black),
                modifier = Modifier.padding(top = MaterialTheme.spacing.small),
            )

            if (item.isCancelled) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.small)
                        .background(CancelledRed.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(MaterialTheme.spacing.small),
                ) {
                    Text(
                        text = "This event has been cancelled",
                        style = descriptionStyle.copy(color = CancelledRed),
                    )
                }
            }

            DetailInfoRow(
                icon = Icons.Filled.CalendarToday,
                text = item.formattedDateLabel(),
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
            )
            DetailInfoRow(
                icon = Icons.Filled.Schedule,
                text = item.formattedTimeRange(),
                modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall),
            )

            if (item.location.isNotEmpty()) {
                EventLocationCard(location = item.location, modifier = Modifier.padding(top = MaterialTheme.spacing.medium))
            }

            // Cancelled events show the banner above instead, and a past
            // event's description isn't relevant anymore either.
            if (!item.isCancelled && !item.isPast && item.description.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.large, bottom = MaterialTheme.spacing.large)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .border(CardBorder, RoundedCornerShape(12.dp))
                        .padding(MaterialTheme.spacing.medium),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Notes,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.padding(end = MaterialTheme.spacing.extraSmall),
                        )
                        Text(text = "Description", style = tagsTitleStyle.copy(fontSize = 12.sp))
                    }
                    // Always shown in full on this dedicated details page - no
                    // "See more" needed here, unlike the space-constrained card.
                    Box(modifier = Modifier.padding(top = MaterialTheme.spacing.small)) {
                        HtmlTextComponent(text = item.description, style = R.style.htmlTextDescription)
                    }
                }
            }
        }

        // Pinned to the bottom of the scaffold, outside the scrollable
        // content above, rather than scrolling away with it.
        Button(
            onClick = { addToDeviceCalendar(context, item) },
            colors = ButtonDefaults.buttonColors(backgroundColor = EventsColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        ) {
            Icon(
                imageVector = Icons.Filled.CalendarToday,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(end = MaterialTheme.spacing.extraSmall),
            )
            Text(text = "Add to my calendar", color = Color.White)
        }
    }
}

/** One white rounded card: the map preview (once/if the address resolves)
 * on top, the address text always underneath - the whole card is tappable
 * to open Google Maps. */
@Composable
private fun EventLocationCard(location: String, modifier: Modifier = Modifier) {
    var latLng by remember(location) { mutableStateOf<LatLng?>(null) }
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(location) {
        latLng = geocodeAddress(context, location)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(CardBorder, RoundedCornerShape(12.dp))
            .clickable { redirectToLink(uriHandler, googleMapsSearchUrl(location)) },
    ) {
        latLng?.let { resolvedLatLng ->
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(resolvedLatLng, 15f)
            }
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(
                    scrollGesturesEnabled = false,
                    zoomGesturesEnabled = false,
                    zoomControlsEnabled = false,
                    myLocationButtonEnabled = false,
                    mapToolbarEnabled = false,
                    compassEnabled = false,
                ),
            ) {
                Marker(position = resolvedLatLng)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                tint = Secondary,
                modifier = Modifier.padding(end = MaterialTheme.spacing.small),
            )
            Text(
                text = location,
                style = descriptionStyle.copy(
                    color = Secondary,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic,
                ),
            )
        }
    }
}

@Composable
private fun DetailInfoRow(
    icon: ImageVector,
    text: String,
    textColor: Color = Color.DarkGray,
    fontStyle: FontStyle? = null,
    modifier: Modifier = Modifier,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textColor,
            modifier = Modifier.padding(end = MaterialTheme.spacing.small),
        )
        Text(
            text = text,
            style = descriptionStyle.copy(color = textColor, fontWeight = FontWeight.Medium, fontStyle = fontStyle),
        )
    }
}

@Composable
private fun Pill(text: String, textColor: Color, backgroundColor: Color, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = tagsTitleStyle.copy(color = textColor, fontSize = 11.sp),
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 4.dp),
    )
}

private fun googleMapsSearchUrl(address: String): String =
    "https://www.google.com/maps/search/?api=1&query=" + Uri.encode(address)

private fun Event.formattedDateLabel(): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH)
    return Instant.ofEpochMilli(startAt).atZone(EVENTS_ZONE).format(formatter)
}

// Hands off to whatever calendar app is already on the device via Android's
// standard insert intent (US2.9) - no extra app permission needed, and this
// degrades gracefully (no-op) if no calendar app is installed.
private fun addToDeviceCalendar(context: Context, event: Event) {
    val intent = Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
        putExtra(CalendarContract.Events.TITLE, event.title)
        putExtra(CalendarContract.Events.DESCRIPTION, event.description)
        putExtra(CalendarContract.Events.EVENT_LOCATION, event.location)
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.startAt)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.endAt)
        putExtra(CalendarContract.Events.ALL_DAY, event.isAllDay)
    }
    runCatching { context.startActivity(intent) }
}
