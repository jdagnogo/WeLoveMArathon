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
fun FoodMenuScreen(
    foodViewModel: FoodViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by foodViewModel.state.collectAsState()
    FoodMenuContent(
        state = state,
        onItemSelected = {
            foodViewModel.dispatchEvent(event = FoodUiEvent.OnCategoryClicked(it))
            navController.navigate(MainDestinations.Food.route)
        },
        onMapSelected = {
            navController.navigate(MainDestinations.Map.createRoute(MapType.Food.key))
        },
        onBackPressed = {
            navController.popBackStack()
        },
        modifier = modifier
    )
}
