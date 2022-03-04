package com.jdagnogo.welovemarathon.shopping.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jdagnogo.welovemarathon.common.submenu.SubMenuScreen
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategories
import com.jdagnogo.welovemarathon.shopping.domain.fakeList

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ShoppingMenuContent(
    state: ShoppingState,
    onItemSelected: (Int) -> Unit = {},
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SubMenuScreen(
        subMenuUiModel = state.subMenuUiModel,
        modifier = modifier,
        onItemSelected = onItemSelected,
        onMapSelected = onMapSelected,
        onBackPressed = onBackPressed
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Preview(name = "Full content")
@Composable
fun ShoppingContentPreview() {
    val reducer = ShoppingReducer()
    val state =
        reducer.reduce(
            ShoppingState(),
            ShoppingPartialState.Error("")
        )
    val finalState =
        reducer.reduce(
            state, ShoppingPartialState.OnShoppingsSuccess(
               items = emptyList(),
                recommendedItems = emptyList()
            )
        )
    MaterialTheme {
        ShoppingMenuContent(
            state = finalState,
            modifier = Modifier,
            onMapSelected = {},
            onBackPressed = {},
            onItemSelected = {})
    }
}
