package com.jdagnogo.welovemarathon.home.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.beach.domain.GetBeachesUseCase
import com.jdagnogo.welovemarathon.common.banner.GetBannerUseCase

@Keep
data class HomeUseCases(
    val getBannerUseCase: GetBannerUseCase,
    val getBeachesUseCase: GetBeachesUseCase,
)
