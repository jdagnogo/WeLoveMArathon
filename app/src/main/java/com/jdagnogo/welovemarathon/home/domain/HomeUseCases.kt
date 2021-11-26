package com.jdagnogo.welovemarathon.home.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.banner.GetHomeBannerUseCase

@Keep
data class HomeUseCases(
    val getBlogUseCase: GetBlogUseCase,
    val getRunUseCase: GetRunUseCase,
    val getHomeBannerUseCase: GetHomeBannerUseCase,
)
