package com.jdagnogo.welovemarathon.run.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.blog.domain.GetBlogUseCase

@Keep
data class RunUseCases(
    val getBlogUseCase: GetBlogUseCase,
    val getRunUseCase: GetRunUseCase,
)
