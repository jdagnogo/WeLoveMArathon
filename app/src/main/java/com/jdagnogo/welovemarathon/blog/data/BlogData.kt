package com.jdagnogo.welovemarathon.blog.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class BlogData(
    val blogDao: BlogDao,
    val blogRemoteData: BlogRemoteData,
    val blogMapper: BlogMapper,
    val dataFreshnessUseCase: DataFreshnessUseCase,
)
