package com.jdagnogo.welovemarathon.common.like.domain

import androidx.annotation.Keep

@Keep
data class FavUseCase (
    val getAllFavUseCases : GetAllFavUseCases,
    val updateFavUseCase : UpdateFavUseCase,
)