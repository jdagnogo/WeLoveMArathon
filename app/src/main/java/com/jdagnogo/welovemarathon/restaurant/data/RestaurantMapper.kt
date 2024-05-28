package com.jdagnogo.welovemarathon.restaurant.data


import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant
import javax.inject.Inject

class RestaurantMapper @Inject constructor() {
    fun toDomain(foodEntities: List<RestaurantEntity>): List<Restaurant> {
        return foodEntities.map {
            it.toRestaurant()
        }
    }

    fun toEntities(foods: List<Restaurant>): List<RestaurantEntity> {
        return foods.map {
            it.toRestaurantEntity()
        }
    }
}