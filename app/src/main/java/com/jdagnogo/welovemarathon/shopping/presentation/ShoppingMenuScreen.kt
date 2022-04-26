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
fun ShoppingMenuScreen(
    shoppingViewModel: ShoppingViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val state by shoppingViewModel.state.collectAsState()
    ShoppingMenuContent(
        state = state,
        onItemSelected = {
            shoppingViewModel.dispatchEvent(event = ShoppingUiEvent.OnCategoryClicked(it))
            navController.navigate(MainDestinations.Shopping.route)
        },
        onMapSelected = {
            navController.navigate(MainDestinations.Map.createRoute(MapType.Shopping.key))
        },
        onBackPressed = {
            navController.popBackStack()
        },
        modifier = modifier
    )
}
