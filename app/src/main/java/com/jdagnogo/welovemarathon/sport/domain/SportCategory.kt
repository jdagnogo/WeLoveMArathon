package com.jdagnogo.welovemarathon.sport.domain

import com.jdagnogo.welovemarathon.common.domain.RightMenuData
import com.jdagnogo.welovemarathon.sport.data.SportCategoryEntity

data class SportCategory(
    val name: String = "",
    val icon: String = "",
    val ordinal: Int = 0,
) {
    fun toSportCategoryEntity(): SportCategoryEntity {
        return SportCategoryEntity(
            name, icon, ordinal
        )
    }

    fun toRightMenuData(): RightMenuData {
        return RightMenuData(
            name = this.name,
            icon = this.icon
        )
    }
}
