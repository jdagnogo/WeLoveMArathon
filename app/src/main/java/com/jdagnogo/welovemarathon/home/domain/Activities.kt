package com.jdagnogo.welovemarathon.home.domain

import androidx.compose.ui.graphics.Color
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.ui.theme.Primary
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryDark
import com.jdagnogo.welovemarathon.common.ui.theme.PrimaryLight

enum class Activities(val title: String, val icon: Int, val backgroundColor: Color) {
    CULTURE("Culture", R.drawable.culture, Primary),
    WINES("Wine", R.drawable.wine, Primary),
    SPORTS("Sports", R.drawable.sports, Primary),
    SHOPPING("Shopping", R.drawable.shopping, Primary),
}
