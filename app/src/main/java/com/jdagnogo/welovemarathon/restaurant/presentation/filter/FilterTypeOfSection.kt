package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.ActivitySubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

val LightModeBlue = Color(0xFF1E4F7B) 

@Composable
fun TypeOfItem(
    modifier: Modifier = Modifier,
    icon: String,
    name: String,
    isSelected: Boolean,
    onItemClicked: () -> Unit = {},
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1f,
        animationSpec = TweenSpec(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        )
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) LightModeBlue else Color.White,
        animationSpec = TweenSpec(durationMillis = 200)
    )

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color.Black,
        animationSpec = TweenSpec(durationMillis = 200)
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) LightModeBlue else Color.Black,
        animationSpec = TweenSpec(durationMillis = 200)
    )
    
    Column(
        modifier = modifier
            .scale(scale)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onItemClicked()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier.size(56.dp),
            color = backgroundColor,
            border = BorderStroke(1.dp, LightModeBlue)
        ) {
            Icon(
                painter = rememberImagePainter(
                    data = icon,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = name,
                tint = iconColor,
                modifier = Modifier
                    .padding(12.dp)
                    .size(32.dp)
            )
        }

        Text(
            text = name,
            color = textColor,
            style = ActivitySubTitleStyle.copy(fontSize = 12.sp),
            modifier = Modifier.padding(top = MaterialTheme.spacing.small)
        )
    }
}