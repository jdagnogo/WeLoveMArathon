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
    RECOMMENDED(R.drawable.food, Primary, "Recommended", "food/Recommended"),
    RESTAURANT(R.drawable.food, Primary, "Restaurant", "food/Restaurant"),
    COFFEE(R.drawable.food, Primary, "Coffees", "food/Coffees"),
    NIGHT(R.drawable.food, Primary, "Night life", "food/Night"),
    ACTIVITY(R.drawable.food, Primary, "Activities", "food/Activities"),
}
