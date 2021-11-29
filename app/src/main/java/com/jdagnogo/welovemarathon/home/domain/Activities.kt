package com.jdagnogo.welovemarathon.home.domain

import androidx.compose.ui.graphics.Color
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight

enum class Activities(val title: String, val icon: Int, val backgroundColor: Color) {
    SHOPPING("Shopping", R.drawable.ic_favorites, PrimaryLight),
    CULTURE("Culture", R.drawable.ic_favorites, Primary),
    SPORTS("Sports", R.drawable.ic_favorites, PrimaryDark),
}
