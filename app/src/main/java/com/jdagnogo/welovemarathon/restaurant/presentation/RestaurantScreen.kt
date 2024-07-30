package com.jdagnogo.welovemarathon.restaurant.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations

@Composable
fun RestaurantScreen(
    viewModel: RestaurantViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    RestaurantContent(
        modifier = modifier,
        state = state,
        onMapSelected = {
            //navController.navigate(MainDestinations.Map.createRoute(MapType.Food.key))
            navController.navigate(MainDestinations.RestaurantFilter.route)
        },
        onBackPressed = {
            navController.popBackStack()
        },
        onRecommendedSelected = {
            viewModel.dispatchEvent(RestaurantUiEvent.OnRestaurantClicked(it))
            navController.navigate(MainDestinations.RestaurantDetails.route)
        },
        onCategoryClicked = {
            viewModel.dispatchEvent(RestaurantUiEvent.OnCategoryClicked(it))
        },
        onLikeClicked = {
            viewModel.dispatchEvent(RestaurantUiEvent.OnLikeClicked(it))
        },
        onRedirectToFilterClicked = {
            navController.navigate(MainDestinations.RestaurantFilter.route)
        },
    )
}
