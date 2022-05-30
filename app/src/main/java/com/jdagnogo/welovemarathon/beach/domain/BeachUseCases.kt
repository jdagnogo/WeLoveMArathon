package com.jdagnogo.welovemarathon.beach.domain

import com.jdagnogo.welovemarathon.food.domain.GetFoodTagUseCase

data class BeachUseCases(
    val getBeachesUseCase: GetBeachesUseCase,
    val getRecommendedBeachesBarsUseCase: GetRecommendedBeachesBarsUseCase,
    val getFoodTagUseCase: GetFoodTagUseCase,
    val getBeachesBarUseCase: GetBeachesBarUseCase,
)
