package com.jdagnogo.welovemarathon.food.domain

import com.jdagnogo.welovemarathon.food.domain.restaurant.GetRestaurantUseCase

data class FoodUseCase(
    val getRestaurantUseCase: GetRestaurantUseCase,
)
