package com.jdagnogo.welovemarathon.food.data.restaurant

import com.jdagnogo.welovemarathon.food.domain.restaurant.Food
import javax.inject.Inject

class RestaurantMapper @Inject constructor() {
    fun toRestaurants(entities: List<FoodEntity>): List<Food> {
        return entities.map {
            it.toFood()
        }
    }

    fun toRestaurantsEntities(foods: List<Food>): List<FoodEntity> {
        return foods.map {
            it.foodEntity()
        }
    }
}
