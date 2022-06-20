package com.jdagnogo.welovemarathon.activities.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.banner.GetBannerUseCase
import com.jdagnogo.welovemarathon.common.like.domain.FavUseCase

@Keep
data class ActivitiesUseCase(
    val getActivitiesUseCase: GetActivitiesUseCase,
    val getActivitiesCategoriesUseCase: GetActivitiesCategoriesUseCase,
    val getActivitiesTagUseCase: GetActivitiesTagUseCase,
    val getBannerUseCase: GetBannerUseCase,
    val favUseCase: FavUseCase
)
