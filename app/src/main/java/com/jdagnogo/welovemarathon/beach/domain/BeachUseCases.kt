package com.jdagnogo.welovemarathon.beach.domain

import com.jdagnogo.welovemarathon.common.like.domain.FavUseCase
import com.jdagnogo.welovemarathon.food.domain.GetFoodTagUseCase

data class BeachUseCases(
    val getBeachesUseCase: GetBeachesUseCase,
    val getRecommendedBeachesBarsUseCase: GetRecommendedBeachesBarsUseCase,
    val getFoodTagUseCase: GetFoodTagUseCase,
    val favUseCase: FavUseCase,
    val getBeachesBarUseCase: GetBeachesBarUseCase,
)
