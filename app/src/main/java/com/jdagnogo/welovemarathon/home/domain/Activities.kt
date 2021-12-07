package com.jdagnogo.welovemarathon.home.domain

import androidx.compose.ui.graphics.Color
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight

enum class Activities(val title: String, val icon: Int, val backgroundColor: Color) {
    SHOPPING("Shopping", R.drawable.ic_wlm_logo, PrimaryLight),
    WINES("Wines", R.drawable.ic_food, Primary),
    CULTURE("Culture", R.drawable.ic_services, PrimaryDark),
    SPORTS("Sports", R.drawable.ic_run, PrimaryLight),
}
