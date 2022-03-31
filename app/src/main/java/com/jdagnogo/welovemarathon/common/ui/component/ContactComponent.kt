package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.contactStyle

@Composable
fun ContactComponent(
    icon: Int = R.drawable.ic_location,
    text: String = "",
    style: TextStyle = contactStyle,
    iconSize: Dp = 32.dp,
    onClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = false),
        ) {
            onClicked()
        }) {
        Icon(
            painterResource(id = icon),
            contentDescription = "map",
            tint = Secondary,
            modifier = Modifier
                .size(iconSize)
        )
        if (text.isNotEmpty()) {
            Text(
                text = text,
                maxLines = 2,
                style = style,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(CenterVertically)
                    .fillMaxWidth()
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
