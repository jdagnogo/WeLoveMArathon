package com.jdagnogo.welovemarathon.common.ui.component

import androidx.annotation.Keep
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.beach.presentation.BeachDetailsScreen
import com.jdagnogo.welovemarathon.beach.presentation.BeachViewModel
import com.jdagnogo.welovemarathon.favorites.FavoritesScreen
import com.jdagnogo.welovemarathon.food.presentation.FoodScreen
import com.jdagnogo.welovemarathon.food.presentation.FoodViewModel
import com.jdagnogo.welovemarathon.home.presentation.HomeScreen
import com.jdagnogo.welovemarathon.home.presentation.HomeViewModel
import com.jdagnogo.welovemarathon.run.presentation.RunScreen
import com.jdagnogo.welovemarathon.run.presentation.RunViewModel
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingScreen
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingViewModel
import com.jdagnogo.welovemarathon.sport.presentation.SportScreen
import com.jdagnogo.welovemarathon.sport.presentation.SportViewModel
import com.jdagnogo.welovemarathon.tips.presentation.TipsScreen
import com.jdagnogo.welovemarathon.tips.presentation.TipsViewModel

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
fun NavGraphBuilder.wlmNavGraph(navController: NavController) {
    navigation(
        route = MainDestinations.Home.route,
        startDestination = HomeSections.HOME.route
    ) {
        homeGraph(navController = navController)
    }

    composable(MainDestinations.Beaches.route,
        arguments = listOf(navArgument("id") { type = NavType.StringType }))
    { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val viewModel = hiltViewModel<BeachViewModel>()
        val beachId = arguments.getString("id")
        BeachDetailsScreen(viewModel = viewModel, beachId = beachId)
    }

    composable(MainDestinations.Shopping.route) {
        val viewModel = hiltViewModel<ShoppingViewModel>()
        ShoppingScreen(viewModel)
    }

    composable(MainDestinations.Sport.route) {
        val viewModel = hiltViewModel<SportViewModel>()
        SportScreen(viewModel)
    }
}

@ExperimentalMaterialApi
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
sealed class MainDestinations(val route: String) {
    object Home : MainDestinations("home")
    object Shopping : MainDestinations("shopping")
    object Sport : MainDestinations("sport")
    object Beaches : MainDestinations("beaches/{id}") {
        fun createRoute(id: String) = "beaches/$id"
    }
}
