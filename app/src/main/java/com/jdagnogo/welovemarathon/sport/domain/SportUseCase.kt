package com.jdagnogo.welovemarathon.sport.domain

import androidx.annotation.Keep

@Keep
data class SportUseCase(
    val getSportsUseCase: GetSportsUseCase,
    val getSportCategoriesUseCase: GetSportCategoriesUseCase,
)
