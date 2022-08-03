package com.jdagnogo.welovemarathon.wine.domain

import androidx.annotation.Keep

@Keep
data class WineUseCases(
    val getWineSocialUseCase: GetWineSocialUseCase,
    val getWineTourUseCase: GetWineTourUseCase,
    val getWineInfoUseCase: GetWineInfoUseCase,
)