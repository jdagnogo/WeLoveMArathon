package com.jdagnogo.welovemarathon.run.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun RunScreen(
    runViewModel: RunViewModel,
    modifier: Modifier = Modifier,
) {
    val state by runViewModel.state.collectAsState()
    RunContent(state = state, modifier = modifier)
}
