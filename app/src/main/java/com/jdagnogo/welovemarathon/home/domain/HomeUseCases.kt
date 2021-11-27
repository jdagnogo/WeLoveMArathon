package com.jdagnogo.welovemarathon.home.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.blog.domain.GetBlogUseCase
import com.jdagnogo.welovemarathon.common.banner.GetHomeBannerUseCase
import com.jdagnogo.welovemarathon.run.domain.GetRunUseCase

@Keep
data class HomeUseCases(
    val getBlogUseCase: GetBlogUseCase,
    val getRunUseCase: GetRunUseCase,
    val getHomeBannerUseCase: GetHomeBannerUseCase,
)
