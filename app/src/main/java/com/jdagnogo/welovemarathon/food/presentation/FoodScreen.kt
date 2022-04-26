package com.jdagnogo.welovemarathon.food.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations
import com.jdagnogo.welovemarathon.map.domain.MapType

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun FoodScreen(
    viewModel: FoodViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    FoodContent(
        state = state,
        onItemSelected = { id ->
            viewModel.dispatchEvent(FoodUiEvent.OnRecommendedItemSelected(id))
        },
        onFiltersSelected = {
            viewModel.dispatchEvent(FoodUiEvent.OnFiltersSelected(it))
        },
        onFilterClicked = {
            viewModel.dispatchEvent(FoodUiEvent.OnFilterClicked(it))
        },
        onResetSelected = {
            viewModel.dispatchEvent(FoodUiEvent.OnResetClicked)
        },
        onMapSelected = {
            navController.navigate(MainDestinations.Map.createRoute(MapType.Food.key))
        },
        onBackPressed = {
            navController.popBackStack()
        },
        onRecommendedDialogClosed = {
            viewModel.dispatchEvent(FoodUiEvent.OnRecommendedDialogClosed)
        },
        modifier = modifier
    )
}
