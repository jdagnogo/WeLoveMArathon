package com.jdagnogo.welovemarathon.common.ui.component

import androidx.annotation.Keep
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.beach.presentation.BeachDetailsScreen
import com.jdagnogo.welovemarathon.beach.presentation.BeachViewModel
import com.jdagnogo.welovemarathon.common.ui.MenuContent
import com.jdagnogo.welovemarathon.favorites.FavoritesScreen
import com.jdagnogo.welovemarathon.food.presentation.FoodScreen
import com.jdagnogo.welovemarathon.food.presentation.FoodViewModel
import com.jdagnogo.welovemarathon.home.presentation.HomeScreen
import com.jdagnogo.welovemarathon.home.presentation.HomeViewModel
import com.jdagnogo.welovemarathon.run.presentation.RunScreen
import com.jdagnogo.welovemarathon.run.presentation.RunViewModel
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingScreen
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingViewModel
import com.jdagnogo.welovemarathon.splash.SplashScreen
import com.jdagnogo.welovemarathon.sport.presentation.SportScreen
import com.jdagnogo.welovemarathon.sport.presentation.SportViewModel
import com.jdagnogo.welovemarathon.tips.presentation.TipsScreen
import com.jdagnogo.welovemarathon.tips.presentation.TipsViewModel
import com.jdagnogo.welovemarathon.wine.presentation.WineScreen
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = GlobalDestinations.SplashScreen.route
    ) {
        composable(GlobalDestinations.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(GlobalDestinations.MainScreen.route) {
            MenuContent()
        }
    }
}

@InternalCoroutinesApi
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

    composable(
        MainDestinations.Beaches.route,
        arguments = listOf(navArgument("currentPage") { type = NavType.IntType })
    )
    { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val viewModel = hiltViewModel<BeachViewModel>()
        val page = arguments.getInt("currentPage")
        BeachDetailsScreen(viewModel = viewModel, currentPage = page)
    }

    composable(MainDestinations.Shopping.route) {
        val viewModel = hiltViewModel<ShoppingViewModel>()
        ShoppingScreen(viewModel)
    }

    composable(MainDestinations.Sport.route) {
        val viewModel = hiltViewModel<SportViewModel>()
        SportScreen(viewModel)
    }
    composable(MainDestinations.Wine.route) {
        WineScreen()
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
    composable(HomeSections.FAVS.route) {
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
    composable(HomeSections.ABOUT.route) {
        FavoritesScreen(modifier)
    }
}

/**
 * Destinations used in the app.
 */

enum class HomeSections(
    val title: String,
    val icon: Int,
    val route: String,
) {
    HOME("Home", R.drawable.ic_wlm_logo, "home/activities"),
    TIPS("Tips", R.drawable.ic_tips, "home/tips"),
    FAVS("Favs", R.drawable.ic_food, "home/favorites"),
    RUN("Home", R.drawable.ic_marathon, "home/run"),
    ABOUT("About", R.drawable.ic_favorites, "home/food")
}

@Keep
sealed class MainDestinations(val route: String) {
    object Home : MainDestinations("home")
    object Shopping : MainDestinations("shopping")
    object Sport : MainDestinations("sport")
    object Wine : MainDestinations("wine")
    object Beaches : MainDestinations("beaches/{currentPage}") {
        fun createRoute(currentPage: Int) = "beaches/$currentPage"
    }
}

sealed class GlobalDestinations(val route: String) {
    object SplashScreen : MainDestinations("splash_screen")
    object MainScreen : MainDestinations("main_screen")
}
