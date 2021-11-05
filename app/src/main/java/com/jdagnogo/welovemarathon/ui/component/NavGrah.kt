package com.jdagnogo.welovemarathon.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jdagnogo.welovemarathon.food.FoodScreen
import com.jdagnogo.welovemarathon.home.HomeScreen
import com.jdagnogo.welovemarathon.sea.SeaScreen
import com.jdagnogo.welovemarathon.shopping.ShoppingScreen

fun NavGraphBuilder.wlmNavGraph() {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.HOME.route
    ) {
        homeGraph()
    }
}

fun NavGraphBuilder.homeGraph(
    modifier: Modifier = Modifier,
) {
    composable(HomeSections.HOME.route) {
        HomeScreen(modifier)
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
}

/**
 * Destinations used in the app.
 */

enum class HomeSections(
    val title: String,
    val icon: ImageVector,
    val route: String,
) {
    HOME("Home", Icons.Outlined.Home, "home/feed"),
    FOOD("Food", Icons.Outlined.Search, "home/food"),
    SEA("Sea", Icons.Outlined.ShoppingCart, "home/sea"),
    SHOPPING("Shopping", Icons.Outlined.AccountCircle, "home/shopping")
}

object MainDestinations {
    const val HOME_ROUTE = "home"
}
