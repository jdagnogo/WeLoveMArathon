package com.jdagnogo.welovemarathon.common.ui.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.food.presentation.FoodScreen
import com.jdagnogo.welovemarathon.food.presentation.FoodViewModel
import com.jdagnogo.welovemarathon.home.presentation.HomeScreen
import com.jdagnogo.welovemarathon.home.presentation.HomeViewModel
import com.jdagnogo.welovemarathon.run.presentation.RunScreen
import com.jdagnogo.welovemarathon.run.presentation.RunViewModel
import com.jdagnogo.welovemarathon.beach.SeaScreen
import com.jdagnogo.welovemarathon.shopping.ShoppingScreen

@ExperimentalFoundationApi
@ExperimentalAnimationApi
fun NavGraphBuilder.wlmNavGraph() {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.HOME.route
    ) {
        homeGraph()
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
fun NavGraphBuilder.homeGraph(
    modifier: Modifier = Modifier,
) {
    composable(HomeSections.HOME.route) {
        val viewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(viewModel, modifier)
    }
    composable(HomeSections.FOOD.route) {
        val viewModel = hiltViewModel<FoodViewModel>()
        FoodScreen(viewModel = viewModel, modifier)
    }
    composable(HomeSections.SERVICES.route) {
        ShoppingScreen(modifier)
    }
    composable(HomeSections.RUN.route) {
        val viewModel = hiltViewModel<RunViewModel>()
        RunScreen(viewModel, modifier)
    }
    composable(HomeSections.FAVORITES.route) {
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
    HOME(R.drawable.ic_home, "home/activities"),
    FOOD(R.drawable.ic_food, "home/food"),
    SERVICES(R.drawable.ic_services, "home/services"),
    RUN(R.drawable.ic_run, "home/run"),
    FAVORITES(R.drawable.ic_favorites, "home/favorites")
}

object MainDestinations {
    const val HOME_ROUTE = "home"
}
