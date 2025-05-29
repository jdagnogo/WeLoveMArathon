package com.jdagnogo.welovemarathon.activities.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.submenu.SubMenuItem
import com.jdagnogo.welovemarathon.activities.data.ActivitiesCategoryEntity
import com.jdagnogo.welovemarathon.map.domain.MapChip

@Keep
data class ActivitiesCategory(
    val name: String = "",
    val icon: String = "",
    val ordinal: Int = 0,
) {
    fun toCategoryEntity(): ActivitiesCategoryEntity {
        return ActivitiesCategoryEntity(
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
