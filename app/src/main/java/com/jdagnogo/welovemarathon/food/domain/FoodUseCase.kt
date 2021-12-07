package com.jdagnogo.welovemarathon.food.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.food.domain.restaurant.GetFoodOthersUseCase
import com.jdagnogo.welovemarathon.food.domain.restaurant.GetFoodRecommendedUseCase

@Keep
data class FoodUseCase(
    val getFoodRecommendedUseCase: GetFoodRecommendedUseCase,
    val getFoodOthersUseCase: GetFoodOthersUseCase,
)
