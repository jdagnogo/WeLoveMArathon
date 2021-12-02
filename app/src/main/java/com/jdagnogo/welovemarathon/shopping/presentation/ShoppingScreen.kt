package com.jdagnogo.welovemarathon.shopping.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@ExperimentalAnimationApi
@Composable
fun ShoppingScreen(
    shoppingViewModel: ShoppingViewModel,
    modifier: Modifier = Modifier,
) {
    val state by shoppingViewModel.state.collectAsState()
    ShoppingContent(state = state,
        onCategoryClicked = {
            shoppingViewModel.dispatchEvent(event = ShoppingUiEvent.OnCategoryClicked(it))
        },
        modifier = modifier)
}
