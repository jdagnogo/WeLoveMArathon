package com.jdagnogo.welovemarathon.tips.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@ExperimentalAnimationApi
@Composable
fun TipsScreen(
    viewModel: TipsViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    TipsContent(state = state, modifier = modifier)
}
