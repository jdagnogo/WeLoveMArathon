package com.jdagnogo.welovemarathon.home.domain

import androidx.annotation.Keep

@Keep
data class HomeUseCases(
    val getBlogUseCase: GetBlogUseCase,
    val getRunUseCase: GetRunUseCase,
)
