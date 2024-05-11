package com.jdagnogo.welovemarathon.restaurant.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations
import com.jdagnogo.welovemarathon.map.domain.MapType

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
        onMapSelected = {
            navController.navigate(MainDestinations.Map.createRoute(MapType.Food.key))
        },
        onBackPressed = {
            viewModel.dispatchEvent(RestaurantUiEvent.OnRestaurantReset)
            navController.popBackStack()
        },
    )
}