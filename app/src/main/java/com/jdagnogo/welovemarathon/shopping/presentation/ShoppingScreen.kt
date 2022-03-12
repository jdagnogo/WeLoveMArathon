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
        onFilterClicked = { viewModel.dispatchEvent(ShoppingUiEvent.OnFilterClicked) },
        onMapSelected = {
            navController.navigate(MainDestinations.Map.route)
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
