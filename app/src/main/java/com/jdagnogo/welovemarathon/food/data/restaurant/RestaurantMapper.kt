package com.jdagnogo.welovemarathon.food.data.restaurant

import com.jdagnogo.welovemarathon.food.domain.restaurant.Restaurant
import javax.inject.Inject

class RestaurantMapper @Inject constructor(){
    fun toRestaurants(entities: List<RestaurantEntity>): List<Restaurant> {
        return entities.map {
            it.toRestaurant()
        }
    }

    fun toRestaurantsEntities(restaurants: List<Restaurant>): List<RestaurantEntity> {
        return restaurants.map {
            it.toRestaurantEntity()
        }
    }
}
