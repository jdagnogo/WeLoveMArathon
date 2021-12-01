package com.jdagnogo.welovemarathon.home.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.beach.domain.GetBeachesUseCase
import com.jdagnogo.welovemarathon.common.banner.GetHomeBannerUseCase

@Keep
data class HomeUseCases(
    val getHomeBannerUseCase: GetHomeBannerUseCase,
    val getBeachesUseCase: GetBeachesUseCase,
)
