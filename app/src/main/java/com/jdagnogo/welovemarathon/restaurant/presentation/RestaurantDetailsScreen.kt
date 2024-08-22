package com.jdagnogo.welovemarathon.restaurant.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun RestaurantDetailsScreen(
    viewModel: RestaurantViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    RestaurantDetailsContent(
        modifier = modifier,
        state = state,
        onLikeClicked = {
            viewModel.dispatchEvent(RestaurantUiEvent.OnLikeClicked(it))
        },
        onBackPressed = {
            viewModel.dispatchEvent(RestaurantUiEvent.OnRestaurantReset)
            navController.popBackStack()
        },
    )
}