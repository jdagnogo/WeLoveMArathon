package com.jdagnogo.welovemarathon.common.ui.component.bottombar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R

@Composable
fun AnimatableIcon(
    icon: Int = R.drawable.ic_wlm_logo,
    scale: Float = 1f,
    isSelected: Boolean = false,
    iconSize: Dp = 36.dp,
    modifier: Modifier = Modifier,
    color: Color
) {
    val primaryBlue = Color(0xFF1E4F7B)
    val lightBlue = Color(0xFF3B7AB5)

    val backgroundColor = if (isSelected) lightBlue else Color.Transparent

    val iconColor = if (isSelected) Color.White else Color.Black

    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        animationSpec = TweenSpec(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )
    val animatedBackgroundColor by animateColorAsState(
        targetValue = backgroundColor,
        animationSpec = TweenSpec(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        )
    )
    val animatedIconColor by animateColorAsState(
        targetValue = iconColor,
        animationSpec = TweenSpec(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        )
    )

    Surface(
        shape = CircleShape,
        color = animatedBackgroundColor,
        modifier = modifier.size(iconSize).scale(animatedScale),
        content = {
            Icon(
                painterResource(id = icon),
                contentDescription = "icon",
                tint = animatedIconColor,
                modifier = Modifier.padding(4.dp)
            )
        }
    )
}
