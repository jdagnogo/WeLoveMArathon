package com.jdagnogo.welovemarathon.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jdagnogo.welovemarathon.food.FoodScreen
import com.jdagnogo.welovemarathon.home.HomeScreen
import com.jdagnogo.welovemarathon.sea.SeaScreen
import com.jdagnogo.welovemarathon.shopping.ShoppingScreen

fun NavGraphBuilder.wlmNavGraph(
    modifier: Modifier = Modifier,
) {
    composable(MainDestinations.Home.route) {
        HomeScreen(modifier)
    }
    composable(MainDestinations.Food.route) {
        FoodScreen(modifier)
    }
    composable(MainDestinations.Shopping.route) {
        ShoppingScreen(modifier)
    }
    composable(MainDestinations.Sea.route) {
        SeaScreen(modifier)
    }
}

/**
 * Destinations used in the app.
 */
sealed class MainDestinations(
    val id: String,
    val icon: ImageVector,
    val route: String,
    val position: Int = 0,
) {
    object Home : MainDestinations("Home", Icons.Outlined.Home, "home/home", 0)
    object Food : MainDestinations("Food", Icons.Outlined.Face, "home/food", 1)
    object Sea : MainDestinations("Sea", Icons.Outlined.Favorite, "home/sea", 2)
    object Shopping : MainDestinations("Shopping", Icons.Outlined.ShoppingCart, "home/shopping", 3)
}
