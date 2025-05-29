package com.jdagnogo.welovemarathon.restaurant.presentation.filter

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R

val LightModeBlueSecond = Color(0xFF1E4F7B)

@ExperimentalMaterialApi
@Composable
fun FilterBooleanComponent(
    modifier: Modifier = Modifier,
    title: Int = R.string.access,
    icon: Int = R.drawable.access,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Column(modifier = modifier.padding(bottom = 4.dp)) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.subtitle1.copy(fontSize = 18.sp, color = Color.Black)
        )

        Spacer(modifier = Modifier.padding(6.dp))

        val scale by animateFloatAsState(
            targetValue = if (isChecked) 1.1f else 1f,
            animationSpec = TweenSpec(
                durationMillis = 200,
                easing = FastOutSlowInEasing
            )
        )

        val backgroundColor by animateColorAsState(
            targetValue = if (isChecked) LightModeBlueSecond else Color.White,
            animationSpec = TweenSpec(durationMillis = 200)
        )

        val iconColor by animateColorAsState(
            targetValue = if (isChecked) Color.White else Color.Black,
            animationSpec = TweenSpec(durationMillis = 200)
        )

        Surface(
            shape = CircleShape,
            border = BorderStroke(1.dp, LightModeBlueSecond),
            color = backgroundColor,
            modifier = Modifier
                .scale(scale)
                .size(56.dp),
            onClick = { onCheckedChange(!isChecked) }
        ) {
            Icon(
                painter = rememberImagePainter(
                    data = icon,
                    builder = {
                        crossfade(true)
                        error(R.drawable.ic_wlm_logo)
                    }
                ),
                contentDescription = stringResource(id = title),
                tint = iconColor,
                modifier = Modifier
                    .padding(12.dp)
                    .size(40.dp)
            )
        }
    }
}