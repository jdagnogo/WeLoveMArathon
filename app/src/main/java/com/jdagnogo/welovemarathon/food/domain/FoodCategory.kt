package com.jdagnogo.welovemarathon.food.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.submenu.SubMenuItem
import com.jdagnogo.welovemarathon.food.data.FoodCategoryEntity
import com.jdagnogo.welovemarathon.map.domain.MapChip

@Keep
data class FoodCategory(
    val name: String = "",
    val icon: String = "",
    val ordinal: Int = 0,
) {
    companion object {
        const val ALL = "All"
        const val FAVS = "Favs"
        val allCategory = FoodCategory(
            name = ALL,
            icon = "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/icons%2FNew%20Food%2Fall.png?alt=media&token=638ab4be-2c4b-46de-8742-62a2cdc2a997",
            ordinal = 99
        )
        val filterCategory = FoodCategory(
            name = "Filter",
            icon = "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/icons%2FNew%20Food%2Ffilter.png?alt=media&token=8319c192-f27c-4975-9aae-4eaf7e9d6807",
            ordinal = 98
        )
        val likedCategory = FoodCategory(
            name = FAVS,
            icon = "https://firebasestorage.googleapis.com/v0/b/welovemarathon-71ff6.appspot.com/o/icons%2FNew%20Food%2Ffavs3.png?alt=media&token=72f868ac-5cac-4737-9cd3-7d268a517aa8",
            ordinal = 97
        )
    }

    fun toCategoryEntity(): FoodCategoryEntity {
        return FoodCategoryEntity(
            name, icon, ordinal
        )
    }

    fun toSubMenuItem(): SubMenuItem {
        return SubMenuItem(
            title = name,
            iconUrl = icon,
            ordinal = ordinal
        )
    }

    fun toMapChip(): MapChip {
        return MapChip(
            name = name,
            key = name,
        )
    }

    fun toFakeFoodCategoryList() = listOf(
        FoodCategory(name = "Fefref", icon = "litora", ordinal = 0),
        FoodCategory(name = "Ferdinand", icon = "litora", ordinal = 1),
        FoodCategory(name = "Huff", icon = "litora", ordinal = 2),
        FoodCategory(name = "Ftotof", icon = "litora", ordinal = 4),
    )
}
