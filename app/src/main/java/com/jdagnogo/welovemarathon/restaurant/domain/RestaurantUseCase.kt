package com.jdagnogo.welovemarathon.restaurant.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.like.domain.FavUseCase
import com.jdagnogo.welovemarathon.food.domain.GetFoodCategoriesUseCase

@Keep
data class RestaurantUseCase(
    val favUseCase: FavUseCase,
    val getRestaurantUseCase: GetRestaurantUseCase,
    val getRestaurantFilterUseCase: GetRestaurantFilterUseCase,
    val getFoodCategoriesUseCase: GetFoodCategoriesUseCase,
)