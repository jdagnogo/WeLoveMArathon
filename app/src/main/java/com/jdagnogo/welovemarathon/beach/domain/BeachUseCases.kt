package com.jdagnogo.welovemarathon.beach.domain

data class BeachUseCases(
    val getBeachesUseCase: GetBeachesUseCase,
    val getRecommendedBeachesBarsUseCase: GetRecommendedBeachesBarsUseCase,
    val getBeachesBarUseCase: GetBeachesBarUseCase,
)
