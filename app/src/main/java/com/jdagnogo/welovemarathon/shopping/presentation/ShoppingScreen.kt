package com.jdagnogo.welovemarathon.shopping.presentation

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
fun ShoppingScreen(
    viewModel: ShoppingViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    ShoppingContent(
        state = state,
        onItemSelected = { id ->
            viewModel.dispatchEvent(ShoppingUiEvent.OnRecommendedItemSelected(id))
        },
        onFiltersSelected = {
            viewModel.dispatchEvent(ShoppingUiEvent.OnFiltersSelected(it))
        },
        onFilterClicked = {
            viewModel.dispatchEvent(ShoppingUiEvent.OnFilterClicked(it))
        },
        onResetSelected = {
            viewModel.dispatchEvent(ShoppingUiEvent.OnResetClicked)
        },
        onMapSelected = {
            navController.navigate(MainDestinations.Map.createRoute(MapType.Shopping.key))
        },
        onBackPressed = {
            navController.popBackStack()
        },
        onRecommendedDialogClosed = {
            viewModel.dispatchEvent(ShoppingUiEvent.OnRecommendedDialogClosed)
        },
        modifier = modifier
    )
}
