package com.jdagnogo.welovemarathon.food.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jdagnogo.welovemarathon.common.submenu.SubMenuInteractions
import com.jdagnogo.welovemarathon.common.submenu.SubMenuScreen

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun FoodMenuContent(
    state: FoodState,
    onItemSelected: (Int) -> Unit = {},
    onMapSelected: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SubMenuScreen(
        subMenuUiModel = state.subMenuUiModel,
        modifier = modifier,
        subMenuInteractions = SubMenuInteractions(
            onItemSelected = onItemSelected,
            onMapSelected = onMapSelected,
            onBackPressed = onBackPressed
        )
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Preview(name = "Full content")
@Composable
fun FoodContentPreview() {
    val reducer = FoodReducer()
    val state =
        reducer.reduce(
            FoodState(),
            FoodPartialState.Error("")
        )
    val finalState =
        reducer.reduce(
            state, FoodPartialState.OnFoodsSuccess(
                items = emptyList(),
                recommendedItems = emptyList()
            )
        )
    MaterialTheme {
        FoodMenuContent(
            state = finalState,
            modifier = Modifier,
            onMapSelected = {},
            onBackPressed = {},
            onItemSelected = {})
    }
}
