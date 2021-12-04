package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.jdagnogo.welovemarathon.R

@Composable
fun ContactComponent(
    icon: Int = R.drawable.ic_location,
    text: String = "",
    textSize: TextUnit = 16.sp,
    iconSize: Dp = 32.dp,
    onClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable { onClicked() }) {
        Image(
            painter = rememberImagePainter(
                data = icon,
            ),
            contentDescription = text,
            modifier = Modifier
                .align(CenterVertically)
                .padding(start = 16.dp)
                .size(iconSize))
        Text(
            text = text,
            maxLines = 2,
            fontSize = textSize,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .align(CenterVertically)
                .fillMaxWidth()
        )
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
