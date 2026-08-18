package com.jdagnogo.welovemarathon.events.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.TitleComponent
import com.jdagnogo.welovemarathon.common.ui.theme.ContentBackgroundLight
import com.jdagnogo.welovemarathon.common.ui.theme.EventsColor
import com.jdagnogo.welovemarathon.common.ui.theme.descriptionStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.ui.theme.tagStyle

private val CancelledRed = Color(0xFFD32F2F)

@Composable
fun NotificationSettingsContent(
    state: NotificationSettingsState,
    onNewEventsToggled: (Boolean) -> Unit = {},
    onCancellationsToggled: (Boolean) -> Unit = {},
    onBackClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ContentBackgroundLight)
            .statusBarsPadding(),
    ) {
        TitleComponent(
            title = "Notifications",
            iconLeft = R.drawable.ic_back,
            onLeftIconClicked = onBackClicked,
            iconRight = null,
        )

        Column(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
            Text(
                text = "PUSH NOTIFICATIONS",
                style = tagStyle.copy(fontSize = 12.sp, color = Color.Gray),
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(MaterialTheme.spacing.medium),
            ) {
                NotificationToggleRow(
                    icon = Icons.Filled.CalendarToday,
                    iconColor = EventsColor,
                    title = "New Events",
                    subtitle = "Alert when a new event is added to the calendar.",
                    checked = state.newEventsEnabled,
                    onCheckedChange = onNewEventsToggled,
                )
                Divider(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium))
                NotificationToggleRow(
                    icon = Icons.Filled.EventBusy,
                    iconColor = CancelledRed,
                    title = "Cancellations",
                    subtitle = "Alert when an event is deleted or cancelled.",
                    checked = state.cancellationsEnabled,
                    onCheckedChange = onCancellationsToggled,
                )
            }
        }
    }
}

@Composable
private fun NotificationToggleRow(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconColor.copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp),
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = MaterialTheme.spacing.medium),
        ) {
            Text(text = title, style = tagStyle.copy(fontSize = 14.sp))
            Text(
                text = subtitle,
                style = descriptionStyle,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = EventsColor,
                checkedTrackColor = EventsColor.copy(alpha = 0.5f),
            ),
        )
    }
}
