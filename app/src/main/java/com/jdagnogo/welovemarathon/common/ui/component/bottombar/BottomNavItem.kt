package com.jdagnogo.welovemarathon.common.ui.component.bottombar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.common.ui.component.HomeSections
import com.jdagnogo.welovemarathon.common.ui.theme.BottomNavTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@Composable
fun BottomNavItem(
    section: HomeSections,
    isSelected: Boolean,
    onItemClicked: () -> Unit = {},
) {
    val color = Secondary.takeIf { isSelected } ?: Primary
    Column(
        modifier = Modifier.clickable(
            indication = rememberRipple(
                bounded = false,
                radius = 30.dp,
                color = PrimaryLight
            ),
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onItemClicked()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatableIcon(
            icon = section.icon,
            scale = if (isSelected) 1.3f else 1f,
            color = color,
        )

        Text(
            text = section.title,
            color = color,
            style = BottomNavTitleStyle,
            modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall)
        )
    }
}

@Preview("Normal")
@Composable
fun BottomNavItemPreview() {
    WeLoveMarathonTheme {
        val section = HomeSections.HOME
        Surface {
            BottomNavItem(
                section = section,
                isSelected = false,
            )
        }
    }
}

@Preview("Selected")
@Composable
fun BottomNavItemSelectedPreview() {
    WeLoveMarathonTheme {
        val section = HomeSections.HOME
        Surface {
            BottomNavItem(
                section = section,
                isSelected = true,
            )
        }
    }
}
