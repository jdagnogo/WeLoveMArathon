package com.jdagnogo.welovemarathon.food.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun FoodScreen(
    viewModel: FoodViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    FoodContent(state,
        onCategorySelected = {
            viewModel.dispatchEvent(FoodUiEvent.OnCategorySelected(type = it))
        },
        modifier = modifier)
}
