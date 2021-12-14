package com.jdagnogo.welovemarathon.sport.domain

import com.jdagnogo.welovemarathon.sport.data.SportCategoryEntity

data class SportCategory(
    val name: String = "",
    val icon: String = "",
) {
    fun toSportCategoryEntity(): SportCategoryEntity {
        return SportCategoryEntity(
            name, icon
        )
    }
}
