package com.jdagnogo.welovemarathon.food.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@ExperimentalFoundationApi
@Composable
fun FoodScreen(
    viewModel: FoodViewModel,
    modifier: Modifier = Modifier,
) {
    //val state by viewModel.state.collectAsState()
    FoodContent(onCategorySelected = {}, modifier = Modifier)
}
