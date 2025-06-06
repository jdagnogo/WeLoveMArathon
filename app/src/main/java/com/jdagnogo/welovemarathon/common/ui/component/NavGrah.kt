@file:OptIn(InternalCoroutinesApi::class, ExperimentalPagerApi::class)

package com.jdagnogo.welovemarathon.common.ui.component

import androidx.annotation.Keep
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
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
import com.jdagnogo.welovemarathon.about.presentation.AboutScreen
import com.jdagnogo.welovemarathon.about.presentation.AboutViewModel
import com.jdagnogo.welovemarathon.activities.presentation.ActivitiesMenuScreen
import com.jdagnogo.welovemarathon.activities.presentation.ActivitiesScreen
import com.jdagnogo.welovemarathon.activities.presentation.ActivitiesViewModel
import com.jdagnogo.welovemarathon.beach.presentation.BeachScreen
import com.jdagnogo.welovemarathon.beach.presentation.BeachViewModel
import com.jdagnogo.welovemarathon.beach.presentation.BeachesBarScreen
import com.jdagnogo.welovemarathon.common.ui.MenuContent
import com.jdagnogo.welovemarathon.culture.presentation.CultureScreen
import com.jdagnogo.welovemarathon.culture.presentation.CultureViewModel
import com.jdagnogo.welovemarathon.culture.presentation.CulturesBarScreen
import com.jdagnogo.welovemarathon.food.presentation.FoodMenuScreen
import com.jdagnogo.welovemarathon.food.presentation.FoodScreen
import com.jdagnogo.welovemarathon.food.presentation.FoodViewModel
import com.jdagnogo.welovemarathon.home.presentation.HomeScreen
import com.jdagnogo.welovemarathon.home.presentation.HomeViewModel
import com.jdagnogo.welovemarathon.map.presentation.MapScreen
import com.jdagnogo.welovemarathon.map.viewmodel.MapViewModel
import com.jdagnogo.welovemarathon.offer.presentation.OfferScreen
import com.jdagnogo.welovemarathon.offer.presentation.OfferViewModel
import com.jdagnogo.welovemarathon.restaurant.presentation.RestaurantDetailsScreen
import com.jdagnogo.welovemarathon.restaurant.presentation.RestaurantScreen
import com.jdagnogo.welovemarathon.restaurant.presentation.RestaurantViewModel
import com.jdagnogo.welovemarathon.restaurant.presentation.filter.FilterScreen
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingMenuScreen
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingScreen
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingViewModel
import com.jdagnogo.welovemarathon.splash.SplashScreen
import com.jdagnogo.welovemarathon.sport.presentation.SportScreen
import com.jdagnogo.welovemarathon.sport.presentation.SportViewModel
import com.jdagnogo.welovemarathon.wine.presentation.WineScreen
import com.jdagnogo.welovemarathon.wine.presentation.WineViewModel
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
fun NavGraphBuilder.wlmNavGraph(
    navController: NavController,
    viewModelStoreOwner: ViewModelStoreOwner
) {
    navigation(
        route = MainDestinations.Home.route,
        startDestination = HomeSections.HOME.route
    ) {
        homeGraph(navController = navController)
    }
    composable(MainDestinations.Beaches.route)
    {
        val viewModel = hiltViewModel<BeachViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        BeachScreen(viewModel = viewModel, navController = navController)
    }

    composable(MainDestinations.Cultures.route)
    {
        val viewModel = hiltViewModel<CultureViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        CultureScreen(viewModel = viewModel, navController = navController)
    }

    composable(MainDestinations.CulturesDetails.route)
    {
        val viewModel = hiltViewModel<CultureViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        CulturesBarScreen(viewModel = viewModel, navController = navController)
    }

    composable(MainDestinations.BeachesBar.route)
    {
        val viewModel = hiltViewModel<BeachViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        BeachesBarScreen(viewModel = viewModel, navController = navController)
    }

    composable(MainDestinations.ShoppingSubMenu.route) {
        val viewModel = hiltViewModel<ShoppingViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        ShoppingMenuScreen(viewModel, navController)
    }

    composable(MainDestinations.FoodSubMenu.route) {
        val viewModel = hiltViewModel<FoodViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        FoodMenuScreen(viewModel, navController)
    }

    composable(MainDestinations.Food.route) {
        val viewModel = hiltViewModel<FoodViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        FoodScreen(viewModel, navController)
    }

    composable(MainDestinations.Restaurant.route) {
        val viewModel =
            hiltViewModel<RestaurantViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        RestaurantScreen(viewModel, navController)
    }

    composable(MainDestinations.RestaurantFilter.route) {
        val viewModel =
            hiltViewModel<RestaurantViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        FilterScreen(viewModel, navController)
    }

    composable(MainDestinations.RestaurantDetails.route) {
        val viewModel =
            hiltViewModel<RestaurantViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        RestaurantDetailsScreen(viewModel, navController)
    }

    composable(MainDestinations.ActivitiesSubMenu.route) {
        val viewModel =
            hiltViewModel<ActivitiesViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        ActivitiesMenuScreen(viewModel, navController)
    }

    composable(MainDestinations.Activities.route) {
        val viewModel =
            hiltViewModel<ActivitiesViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        ActivitiesScreen(viewModel, navController)
    }

    composable(MainDestinations.Shopping.route) {
        val viewModel = hiltViewModel<ShoppingViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        ShoppingScreen(viewModel, navController)
    }

    composable(
        MainDestinations.Map.route,
        arguments = listOf(navArgument(KEY_MAP_TYPE) { type = NavType.StringType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val mapType = arguments.getString(KEY_MAP_TYPE) ?: ""
        val viewModel = hiltViewModel<MapViewModel>(viewModelStoreOwner = viewModelStoreOwner)
        MapScreen(mapType, viewModel, navController)
    }

    composable(MainDestinations.Sport.route) {
        val viewModel = hiltViewModel<SportViewModel>()
        SportScreen(viewModel)
    }
    composable(MainDestinations.Wine.route) {
        val viewModel = hiltViewModel<WineViewModel>()
        WineScreen(viewModel = viewModel, navController)
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
    composable(HomeSections.OFFERS.route) {
        val viewModel = hiltViewModel<OfferViewModel>()
        OfferScreen(modifier, navController, viewModel)
    }
    composable(HomeSections.ABOUT.route) {
        val viewModel = hiltViewModel<AboutViewModel>()
        AboutScreen(viewModel, navController, modifier)
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
    HOME("Home", R.drawable.home, "home/activities"),
    OFFERS("Offers", R.drawable.fav, "home/offers"),
    ABOUT("About", R.drawable.about, "home/food")
}

@Keep
sealed class MainDestinations(val route: String) {
    object Home : MainDestinations("home")
    object Food : MainDestinations("food")
    object Restaurant : MainDestinations("restaurant")
    object RestaurantFilter : MainDestinations("restaurantFilter")
    object RestaurantDetails : MainDestinations("restaurantDetails")
    object Activities : MainDestinations("activities")
    object ShoppingSubMenu : MainDestinations("shoppingSubMenu")
    object FoodSubMenu : MainDestinations("foodSubMenu")
    object ActivitiesSubMenu : MainDestinations("activitiesSubMenu")
    object Shopping : MainDestinations("shopping")
    object Map : MainDestinations("map/{$KEY_MAP_TYPE}") {
        fun createRoute(mapType: String) = "map/$mapType"
    }

    object BeachesBar : MainDestinations("beachesBar")
    object Sport : MainDestinations("sport")
    object Wine : MainDestinations("wine")
    object Beaches : MainDestinations("beaches")
    object Cultures : MainDestinations("cultures")
    object CulturesDetails : MainDestinations("culturesDetails")
}

sealed class GlobalDestinations(val route: String) {
    object SplashScreen : MainDestinations("splash_screen")
    object MainScreen : MainDestinations("main_screen")
}

const val KEY_MAP_TYPE = "mapType"
