package com.jdagnogo.welovemarathon.food.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.food.domain.restaurant.GetCoffeeUseCase
import com.jdagnogo.welovemarathon.food.domain.restaurant.GetDessertsUseCase
import com.jdagnogo.welovemarathon.food.domain.restaurant.GetRestaurantUseCase
@Keep
data class FoodUseCase(
    val getRestaurantUseCase: GetRestaurantUseCase,
    val getCoffeeUseCase: GetCoffeeUseCase,
    val getDessertsUseCase: GetDessertsUseCase,
)
