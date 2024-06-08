package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.component.HomeSections
import com.jdagnogo.welovemarathon.common.ui.theme.ActivitySubTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.BottomNavTitleStyle
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.common.ui.theme.White
import com.jdagnogo.welovemarathon.common.ui.theme.spacing

@Composable
fun TypeOfItem(
    modifier: Modifier = Modifier,
    icon: String,
    name: String,
    isSelected: Boolean,
    onItemClicked: () -> Unit = {},
) {
    val color = Secondary.takeIf { isSelected } ?: PrimaryLight
    val textColor = Secondary.takeIf { isSelected } ?: Color.White
    val scale = if (isSelected) 1.3f else 1f
    val animatedScale: Float by animateFloatAsState(
        targetValue = scale,
        animationSpec = TweenSpec(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ), label = ""
    )
    val animatedColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ), label = ""
    )
    val animatedTextColor by animateColorAsState(
        targetValue = textColor,
        animationSpec = TweenSpec(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ), label = ""
    )
    Column(
        modifier = modifier.clickable(
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
        // Animation params

        Surface(
            shape = CircleShape,
            modifier = Modifier.scale(animatedScale),
            content = {
                Icon(
                    painter = rememberImagePainter(
                        data = icon,
                        builder = {
                            crossfade(true)
                            error(R.drawable.ic_wlm_logo)
                        }
                    ),
                    contentDescription = name,
                    tint = White,
                    modifier = modifier.background(color = animatedColor).size(56.dp)

                )
            }
        )

        Text(
            text = name,
            color = animatedTextColor,
            style = ActivitySubTitleStyle.copy(fontSize = 10.sp),
            modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall)
        )
    }
}
