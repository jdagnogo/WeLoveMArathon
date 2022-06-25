package com.jdagnogo.welovemarathon.wine.presentation

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.wine.domain.WineTour

@Composable
fun WineTourComponent(
    wineTours: List<WineTour>,
    modifier: Modifier = Modifier,
) {
}

@Composable
fun WineTourContentComponent(
    wineTours: WineTour,
    modifier: Modifier = Modifier,
) {
}

@Preview
@Composable
fun WineTourComponentContentPreview() {
    WeLoveMarathonTheme {
        Surface {
            val wineTour = WineTour(
                name = "Full visite",
                icon = "",
                description = "Description",
                images = listOf(),
                hour = "",
                ordinal = 0,
                id = ""
            )
            WineTourContentComponent(wineTour)
        }
    }
}