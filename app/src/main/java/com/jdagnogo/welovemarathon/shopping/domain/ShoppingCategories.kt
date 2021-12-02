package com.jdagnogo.welovemarathon.shopping.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.R

@Keep
enum class ShoppingCategories(val icon: Int = R.drawable.ic_wlm_logo) {
    Woman,
    Man,
    Kid,
    Pet,
    Home,
    Kosmima,
    Optical,
    Sports,
    Technology,
    Books,
    Flower,
    More,
}
