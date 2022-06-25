package com.jdagnogo.welovemarathon.wine.presentation

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jdagnogo.welovemarathon.common.ui.theme.WeLoveMarathonTheme
import com.jdagnogo.welovemarathon.wine.domain.WineSocial

@Composable
fun SocialMediaComponent(
    wineSocial: List<WineSocial>,
    modifier: Modifier = Modifier,
) {

}

@Composable
fun SocialMediaItemComponent(
    wineSocial: WineSocial,
    modifier: Modifier = Modifier,
) {

}

@Preview
@Composable
fun SocialMediaComponentPreview() {
    WeLoveMarathonTheme {
        Surface {
            val wineSocial = WineSocial(
                name= "FaceBook",
                link = "https//blabla",
                icon = "",
                id = "",
            )
            SocialMediaItemComponent(wineSocial)
        }
    }
}