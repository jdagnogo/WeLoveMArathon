package com.jdagnogo.welovemarathon.shopping.domain

import com.jdagnogo.welovemarathon.common.submenu.SubMenuItem
import com.jdagnogo.welovemarathon.shopping.data.ShoppingCategoryEntity

data class ShoppingCategory(
    val name: String = "",
    val icon: String = "",
    val ordinal: Int = 0,
) {
    fun toCategoryEntity(): ShoppingCategoryEntity {
        return ShoppingCategoryEntity(
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
}
