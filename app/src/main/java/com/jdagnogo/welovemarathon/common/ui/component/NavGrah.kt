package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jdagnogo.welovemarathon.food.FoodScreen
import com.jdagnogo.welovemarathon.home.presentation.HomeScreen
import com.jdagnogo.welovemarathon.home.presentation.HomeViewModel
import com.jdagnogo.welovemarathon.sea.SeaScreen
import com.jdagnogo.welovemarathon.shopping.ShoppingScreen
@ExperimentalAnimationApi
fun NavGraphBuilder.wlmNavGraph() {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.HOME.route
    ) {
        homeGraph()
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.homeGraph(
    modifier: Modifier = Modifier,
) {
    composable(HomeSections.HOME.route) {
        val viewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(viewModel, modifier)
    }
    composable(HomeSections.FOOD.route) {
        FoodScreen(modifier)
    }
    composable(HomeSections.SEA.route) {
        ShoppingScreen(modifier)
    }
    composable(HomeSections.SHOPPING.route) {
        SeaScreen(modifier)
    }
    composable(HomeSections.MUSEUM.route) {
        SeaScreen(modifier)
    }
}

/**
 * Destinations used in the app.
 */

enum class HomeSections(
    val icon: ImageVector,
    val route: String,
) {
    HOME(Icons.Outlined.Home, "home/feed"),
    FOOD(Icons.Outlined.Search, "home/food"),
    SEA(Icons.Outlined.ShoppingCart, "home/sea"),
    SHOPPING(Icons.Outlined.AccountCircle, "home/shopping"),
    MUSEUM(Icons.Outlined.AccountCircle, "home/museam")
}

object MainDestinations {
    const val HOME_ROUTE = "home"
}
