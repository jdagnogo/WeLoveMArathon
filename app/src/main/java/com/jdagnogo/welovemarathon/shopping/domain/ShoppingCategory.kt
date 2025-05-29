package com.jdagnogo.welovemarathon.shopping.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.submenu.SubMenuItem
import com.jdagnogo.welovemarathon.map.domain.MapChip
import com.jdagnogo.welovemarathon.shopping.data.ShoppingCategoryEntity
@Keep
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

    fun toMapChip(): MapChip {
        return MapChip(
            name = name,
            key = name,
        )
    }
}
