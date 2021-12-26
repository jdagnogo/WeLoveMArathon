package com.jdagnogo.welovemarathon.food.domain

import androidx.compose.ui.graphics.Color
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Primary

enum class FoodCategory(
    val icon: Int,
    val color: Color,
    val title: String = "",
    val route: String,
) {
    RESTAURANT(R.drawable.food, Primary, "Restaurant", "food/Restaurant"),
    COFFEE(R.drawable.capuchino, Primary, "Coffee", "food/Coffees"),
    DESSERT(R.drawable.dessert, Primary, "Desert", "food/Deserts"),
}
