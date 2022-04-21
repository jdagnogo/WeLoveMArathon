package com.jdagnogo.welovemarathon.food.data

import com.jdagnogo.welovemarathon.food.domain.Food
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.food.domain.FoodTag
import javax.inject.Inject

class FoodMapper @Inject constructor() {
    fun toDomain(foodEntities: List<FoodEntity>): List<Food> {
        return foodEntities.map {
            it.toFood()
        }
    }

    fun toEntities(foods: List<Food>): List<FoodEntity> {
        return foods.map {
            it.toFoodEntity()
        }
    }

    fun toDomainCategories(entities: List<FoodCategoryEntity>): List<FoodCategory> {
        return entities.map {
            it.toDomainCategory()
        }
    }

    fun toEntitiesCategories(categories: List<FoodCategory>): List<FoodCategoryEntity> {
        return categories.map {
            it.toCategoryEntity()
        }
    }

    fun toDomainTag(entities: List<FoodTagEntity>): List<FoodTag> {
        return entities.map {
            it.toDomainCategory()
        }
    }

    fun toEntitiesTag(tag: List<FoodTag>): List<FoodTagEntity> {
        return tag.map {
            it.toEntity()
        }
    }
}
