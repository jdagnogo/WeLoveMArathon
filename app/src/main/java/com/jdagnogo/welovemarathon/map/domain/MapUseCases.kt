package com.jdagnogo.welovemarathon.map.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.activities.domain.GetActivitiesCategoriesUseCase
import com.jdagnogo.welovemarathon.activities.domain.GetActivitiesUseCase
import com.jdagnogo.welovemarathon.beach.domain.GetBeachesBarUseCase
import com.jdagnogo.welovemarathon.beach.domain.GetBeachesUseCase
import com.jdagnogo.welovemarathon.culture.domain.GetCulturesUseCase
import com.jdagnogo.welovemarathon.food.domain.GetFoodCategoriesUseCase
import com.jdagnogo.welovemarathon.food.domain.GetFoodUseCase
import com.jdagnogo.welovemarathon.restaurant.domain.GetRestaurantUseCase
import com.jdagnogo.welovemarathon.shopping.domain.GetShoppingCategoriesUseCase
import com.jdagnogo.welovemarathon.shopping.domain.GetShoppingUseCase

@Keep
data class MapUseCases(
    val getShoppingUseCase: GetShoppingUseCase,
    val getShoppingCategoriesUseCase: GetShoppingCategoriesUseCase,
    val getActivitiesUseCase: GetActivitiesUseCase,
    val getActivitiesCategoriesUseCase: GetActivitiesCategoriesUseCase,
    val getBeachesUseCase: GetBeachesUseCase,
    val getRestaurantUseCase: GetRestaurantUseCase,
    val getFoodCategoriesUseCase: GetFoodCategoriesUseCase,
    val getBeachesBarUseCase: GetBeachesBarUseCase,
)
