package com.jdagnogo.welovemarathon.run.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark

@Composable
fun RunTitleComponent(title : String, alignRight: Boolean, modifier: Modifier) {
    Box(modifier = modifier
        .padding(16.dp)) {
        // TODO : Background image
        Text(text = title,
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            color = PrimaryDark,
            modifier = Modifier
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
        RunTitleComponent("Align right",modifier = Modifier.fillMaxWidth(), alignRight = true)
    }
}

@ExperimentalAnimationApi
@Preview(name = "Align left", showBackground = true)
@Composable
fun RunTitleTitleComponentPreview() {
    MaterialTheme {
        RunTitleComponent("Align left", modifier = Modifier.fillMaxWidth(), alignRight = false)
    }
}
