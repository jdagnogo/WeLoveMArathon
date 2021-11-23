package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jdagnogo.welovemarathon.R

@Composable
fun LoadingComponent(rawId: Int, modifier: Modifier) {
    Box(modifier = modifier) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawId))
        val progress by animateLottieCompositionAsState(composition, iterations = Int.MAX_VALUE)
        LottieAnimation(
            composition,
            progress,
        )
    }
}

@Preview
@Composable
fun LoadingComponentPreview() {
    MaterialTheme {
        LoadingComponent(modifier = Modifier, rawId = R.raw.blog)
    }
}
