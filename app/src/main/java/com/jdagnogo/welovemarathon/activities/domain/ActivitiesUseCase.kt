package com.jdagnogo.welovemarathon.activities.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.banner.GetBannerUseCase

@Keep
data class ActivitiesUseCase(
    val getActivitiesUseCase: GetActivitiesUseCase,
    val getActivitiesCategoriesUseCase: GetActivitiesCategoriesUseCase,
    val getActivitiesTagUseCase: GetActivitiesTagUseCase,
    val getBannerUseCase: GetBannerUseCase
)
