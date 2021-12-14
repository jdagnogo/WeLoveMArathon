package com.jdagnogo.welovemarathon.sport.data

import com.jdagnogo.welovemarathon.sport.domain.Sport
import com.jdagnogo.welovemarathon.sport.domain.SportCategory
import javax.inject.Inject

class SportMapper @Inject constructor() {
    fun toSports(entities: List<SportEntity>): List<Sport> {
        return entities.map {
            it.toSport()
        }
    }

    fun toSportsEntities(Sports: List<Sport>): List<SportEntity> {
        return Sports.map {
            it.toSportEntity()
        }
    }

    fun toSportCategories(entities: List<SportCategoryEntity>): List<SportCategory> {
        return entities.map {
            it.toSportCategory()
        }
    }

    fun toSportCategoriesEntities(categories: List<SportCategory>): List<SportCategoryEntity> {
        return categories.map {
            it.toSportCategoryEntity()
        }
    }
}
