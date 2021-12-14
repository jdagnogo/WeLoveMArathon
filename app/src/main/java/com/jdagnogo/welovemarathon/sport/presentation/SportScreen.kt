package com.jdagnogo.welovemarathon.sport.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@ExperimentalAnimationApi
@Composable
fun SportScreen(
    viewModel: SportViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    SportContent(
        state = state,
        onCategoryClicked = {
            viewModel.dispatchEvent(event = SportUiEvent.OnCategorySelected(it))
        }, modifier = modifier)
}
