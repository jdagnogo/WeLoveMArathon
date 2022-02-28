package com.jdagnogo.welovemarathon.shopping.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun ShoppingMenuScreen(
    shoppingViewModel: ShoppingViewModel,
    modifier: Modifier = Modifier,
) {
    val state by shoppingViewModel.state.collectAsState()
    ShoppingMenuContent(state = state,
        onItemSelected = {
            shoppingViewModel.dispatchEvent(event = ShoppingUiEvent.OnCategoryClicked(it))
        },
        onMapSelected = {
            shoppingViewModel.dispatchEvent(event = ShoppingUiEvent.OnMapSelected)
        },
        modifier = modifier)
}
