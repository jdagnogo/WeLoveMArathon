package com.jdagnogo.welovemarathon.restaurant.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun RestaurantDetailsScreen(
    viewModel: RestaurantViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    val restaurant = state.currentRestaurantSelected ?: return

    RestaurantDetailsContent(
        modifier = modifier,
        restaurant = restaurant,
        onLikeClicked = {
            viewModel.dispatchEvent(RestaurantUiEvent.OnLikeClicked(it))
        },
        onBackPressed = {
            viewModel.dispatchEvent(RestaurantUiEvent.OnRestaurantReset)
            navController.popBackStack()
        },
    )
}