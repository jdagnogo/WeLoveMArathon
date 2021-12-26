package com.jdagnogo.welovemarathon.shopping.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.domain.RightMenuData

@Keep
enum class ShoppingCategories(val icon: Int = R.drawable.ic_wlm_logo) {
    Woman(R.drawable.ic_woman),
    Man(R.drawable.ic_man),
    Kid(R.drawable.ic_kids),
    Pet(R.drawable.ic_pet),
    Home,
    Kosmima,
    Optical,
    Sports,
    Technology,
    Books,
    Flower,
    More,
}

fun ShoppingCategories.toRightMenuData(): RightMenuData {
    return RightMenuData(
        name = this.name,
        iconRes = this.icon
    )
}
