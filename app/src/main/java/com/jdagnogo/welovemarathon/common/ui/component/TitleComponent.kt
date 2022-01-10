package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark

@Composable
fun TitleComponent(title: String, alignRight: Boolean = false, modifier: Modifier) {
    Box(modifier = modifier) {
        Text(text = title,
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            color = PrimaryDark,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                .align(
                    Alignment.TopEnd.takeIf { alignRight } ?: Alignment.TopStart)

        )
    }
}

@ExperimentalAnimationApi
@Preview(name = "Align right", showBackground = true)
@Composable
fun RunTitleRightComponentPreview() {
    MaterialTheme {
        TitleComponent("Align right", modifier = Modifier.fillMaxWidth(), alignRight = true)
    }
}

@ExperimentalAnimationApi
@Preview(name = "Align left", showBackground = true)
@Composable
fun RunTitleTitleComponentPreview() {
    MaterialTheme {
        TitleComponent("Align left", modifier = Modifier.fillMaxWidth(), alignRight = false)
    }
}
