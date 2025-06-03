package com.jdagnogo.welovemarathon.common.ui.component.bottombar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.HomeSections
import com.jdagnogo.welovemarathon.common.ui.theme.BottomNavTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@Composable
fun BottomNavItem(
    section: HomeSections,
    isSelected: Boolean,
    hasNewOffer: Boolean = false,
    onItemClicked: () -> Unit = {}
) {
    val rippleColor = Color(0xFF5B91C7)

    Column(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onItemClicked()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            AnimatableIcon(
                icon = section.icon,
                scale = if (isSelected) 1.1f else 1f,
                isSelected = isSelected,
                iconSize = 40.dp,
                color = Color(0xFF1E4F7B)
            )

            Crossfade(
                targetState = section == HomeSections.OFFERS && hasNewOffer && !isSelected,
                modifier = Modifier.align(Alignment.TopEnd)
            ) { shouldShow ->
                if (shouldShow) {
                    Image(
                        painter = painterResource(id = R.drawable.exclamation),
                        contentDescription = "New offer available",
                        modifier = Modifier
                            .size(12.dp)
                            .offset(x = 6.dp, y = (0).dp)
                    )
                }
            }
        }

        Text(
            text = section.title,
            color = if (isSelected) Color(0xFF1E4F7B) else Color.Black,
            style = BottomNavTitleStyle,
            modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall)
        )
    }
}

@Preview("Normal")
@Composable
fun BottomNavItemPreview() {
    WeLoveMarathonTheme {
        val section = HomeSections.OFFERS
        Surface {
            BottomNavItem(
                section = section,
                isSelected = false,
                hasNewOffer = true
            )
        }
    }
}

@Preview("Selected")
@Composable
fun BottomNavItemSelectedPreview() {
    WeLoveMarathonTheme {
        val section = HomeSections.OFFERS
        Surface {
            BottomNavItem(
                section = section,
                isSelected = true,
                hasNewOffer = true
            )
        }
    }
}