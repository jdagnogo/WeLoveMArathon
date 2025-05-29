package com.jdagnogo.welovemarathon.food.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.banner.GetBannerUseCase
import com.jdagnogo.welovemarathon.common.like.domain.FavUseCase

@Keep
data class FoodUseCase(
    val getFoodUseCase: GetFoodUseCase,
    val getFoodCategoriesUseCase: GetFoodCategoriesUseCase,
    val getFoodTagUseCase: GetFoodTagUseCase,
    val favUseCase: FavUseCase,
    val getBannerUseCase: GetBannerUseCase
)
