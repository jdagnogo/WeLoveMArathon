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
import com.jdagnogo.welovemarathon.R
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
    val icon: Int,
    val route: String,
) {
    HOME(R.drawable.ic_home, "home/feed"),
    FOOD(R.drawable.ic_food, "home/food"),
    SEA(R.drawable.ic_sea, "home/sea"),
    SHOPPING(R.drawable.ic_shopping, "home/shopping"),
    MUSEUM(R.drawable.ic_food, "home/museam")
}

object MainDestinations {
    const val HOME_ROUTE = "home"
}
