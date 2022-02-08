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
import com.jdagnogo.welovemarathon.common.ui.theme.Primary

@Composable
fun AnimatableIcon(
    icon: Int = R.drawable.ic_wlm_logo,
    scale: Float = 1f,
    color: Color = Primary,
    iconSize: Dp = 30.dp,
    modifier: Modifier = Modifier
) {
    // Animation params
    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        animationSpec = TweenSpec(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        )
    )
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        )
    )
    Surface(
        shape = CircleShape,
        modifier = modifier.size(iconSize).scale(animatedScale),
        content = {
            Icon(
                painterResource(id = icon),
                contentDescription = "icon",
                tint = Color.White,
                modifier = modifier.background(color = animatedColor)
                    .padding(2.dp)
            )
        }
    )
}
