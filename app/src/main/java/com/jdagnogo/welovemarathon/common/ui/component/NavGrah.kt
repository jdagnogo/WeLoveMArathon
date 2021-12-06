package com.jdagnogo.welovemarathon.common.ui.component

import androidx.annotation.Keep
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.beach.presentation.BeachDetailsScreen
import com.jdagnogo.welovemarathon.beach.presentation.BeachViewModel
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations.BEACHES_ROUTE
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations.SHOPPING_ROUTE
import com.jdagnogo.welovemarathon.favorites.FavoritesScreen
import com.jdagnogo.welovemarathon.food.presentation.FoodScreen
import com.jdagnogo.welovemarathon.food.presentation.FoodViewModel
import com.jdagnogo.welovemarathon.home.presentation.HomeScreen
import com.jdagnogo.welovemarathon.home.presentation.HomeViewModel
import com.jdagnogo.welovemarathon.run.presentation.RunScreen
import com.jdagnogo.welovemarathon.run.presentation.RunViewModel
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingScreen
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingViewModel
import com.jdagnogo.welovemarathon.tips.presentation.TipsScreen
import com.jdagnogo.welovemarathon.tips.presentation.TipsViewModel

@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
fun NavGraphBuilder.wlmNavGraph(navController: NavController) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.HOME.route
    ) {
        homeGraph(navController = navController)
    }

    composable(BEACHES_ROUTE) {
        val viewModel = hiltViewModel<BeachViewModel>()
        BeachDetailsScreen(viewModel = viewModel)
    }

    composable(SHOPPING_ROUTE) {
        val viewModel = hiltViewModel<ShoppingViewModel>()
        ShoppingScreen(viewModel)
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
fun NavGraphBuilder.homeGraph(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composable(HomeSections.HOME.route) {
        val viewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(viewModel, navController, modifier)
    }
    composable(HomeSections.FOOD.route) {
        val viewModel = hiltViewModel<FoodViewModel>()
        FoodScreen(viewModel = viewModel, modifier)
    }
    composable(HomeSections.TIPS.route) {
        val viewModel = hiltViewModel<TipsViewModel>()
        TipsScreen(viewModel, modifier)
    }
    composable(HomeSections.RUN.route) {
        val viewModel = hiltViewModel<RunViewModel>()
        RunScreen(viewModel, modifier)
    }
    composable(HomeSections.FAVORITES.route) {
        FavoritesScreen(modifier)
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
    TIPS(R.drawable.ic_services, "home/tips"),
    RUN(R.drawable.ic_run, "home/run"),
    FAVORITES(R.drawable.ic_favorites, "home/favorites")
}

@Keep
object MainDestinations {
    const val HOME_ROUTE = "home"
    const val BEACHES_ROUTE = "beaches"
    const val SHOPPING_ROUTE = "shopping"
    const val FOOD_ROUTE = "food"
    const val SPORT_ROUTE = "sport"
    const val CULTURE_ROUTE = "cultures"
}
