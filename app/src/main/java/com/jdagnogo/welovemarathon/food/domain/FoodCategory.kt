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
        const val ALL = "all"
        const val FAVS = "favs"
        val allCategory = FoodCategory(name = ALL, ordinal = 99)
        val filterCategory = FoodCategory(name = "filter", ordinal = 98)
        val likedCategory = FoodCategory(name = FAVS, ordinal = 97)
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
