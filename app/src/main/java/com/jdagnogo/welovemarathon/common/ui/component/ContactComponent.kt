package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.contactStyle
import com.jdagnogo.welovemarathon.common.ui.theme.spacing
import com.jdagnogo.welovemarathon.common.utils.conditional

@Composable
fun ContactComponent(
    icon: Int = R.drawable.ic_location,
    text: String = "",
    textColor: Color = Color.Black,
    style: TextStyle = contactStyle,
    iconSize: Dp = 32.dp,
    tint: Color = Secondary,
    onClicked: () -> Unit = {},
    backgroundColor: Color? = null,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    painter: Painter? = null,
    textPadding: Dp = MaterialTheme.spacing.small
) {
    if (text.isEmpty()) {
        Box(
            modifier = modifier
                .size(iconSize + 16.dp)
                .background(backgroundColor ?: Color.Transparent, CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = onClicked
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painter ?: painterResource(id = icon),
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(iconSize)
            )
        }
    } else {
        Row(
            modifier = modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false),
                    onClick = onClicked
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(iconSize + 16.dp)
                    .background(backgroundColor ?: Color.Transparent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painter ?: painterResource(id = icon),
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(iconSize)
                )
            }
            Text(
                text = text,
                color = textColor,
                style = style,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = textPadding)
            )
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun ContactComponentPreview() {
    MaterialTheme {
        ContactComponent(R.drawable.ic_location, "My address")
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun ContactComponentWithoutTextPreview() {
    MaterialTheme {
        ContactComponent(R.drawable.ic_location)
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun ContactComponentWithoutTextWithBackgroundPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.background(Color.Red)) {
            ContactComponent(R.drawable.ic_location, backgroundColor = Color.Black)
        }
    }
}
