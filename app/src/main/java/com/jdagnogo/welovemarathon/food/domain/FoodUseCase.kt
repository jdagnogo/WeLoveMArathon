package com.jdagnogo.welovemarathon.food.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.banner.GetBannerUseCase

@Keep
data class FoodUseCase(
    val getFoodUseCase: GetFoodUseCase,
    val getFoodCategoriesUseCase: GetFoodCategoriesUseCase,
    val getFoodTagUseCase: GetFoodTagUseCase,
    val getBannerUseCase: GetBannerUseCase
)
