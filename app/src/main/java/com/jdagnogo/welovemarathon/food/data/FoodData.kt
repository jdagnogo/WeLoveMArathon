package com.jdagnogo.welovemarathon.food.data

import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantDao
import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantMapper
import com.jdagnogo.welovemarathon.food.domain.restaurant.RestaurantRemoteData

data class FoodData(
    val restaurantDao: RestaurantDao,
    val restaurantMapper: RestaurantMapper,
    val restaurantRemoteData: RestaurantRemoteData
)
