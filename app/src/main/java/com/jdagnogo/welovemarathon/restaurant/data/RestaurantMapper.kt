package com.jdagnogo.welovemarathon.restaurant.data


import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantFilter
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

    fun toDomainFilter(foodEntities: List<RestaurantFilterEntity>): List<RestaurantFilter> {
        return foodEntities.map {
            it.toRestaurantFilter()
        }
    }

    fun toEntitiesFilter(foods: List<RestaurantFilter>): List<RestaurantFilterEntity> {
        return foods.map {
            it.toRestaurantFilterEntity()
        }
    }
}