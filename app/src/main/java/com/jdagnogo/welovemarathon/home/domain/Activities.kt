package com.jdagnogo.welovemarathon.home.domain

import androidx.compose.ui.graphics.Color
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.ActivityColor
import com.jdagnogo.welovemarathon.common.ui.theme.BeachColor
import com.jdagnogo.welovemarathon.common.ui.theme.CultureColor
import com.jdagnogo.welovemarathon.common.ui.theme.FoodColor
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.ShoppingColor
import com.jdagnogo.welovemarathon.common.ui.theme.WineColor

enum class Activities(
    val title: String,
    val subtitle: String,
    val icon: Int,
    val backgroundColor: Color
) {
    ACTIVITIES("Activities", "Let's explore", R.drawable.ic_activity, ActivityColor),
    BEACHES("Beaches", "Dive into", R.drawable.ic_beach, BeachColor),
    CULTURE("Culture", "Find treasures", R.drawable.culture, CultureColor),
    SHOPPING("Shopping", "Therapy time", R.drawable.shopping, ShoppingColor),
    FOOD("Food&Drink", "Yummy", R.drawable.food, FoodColor),
    WINES("Wine", "Deserve it", R.drawable.wine, WineColor),
}
