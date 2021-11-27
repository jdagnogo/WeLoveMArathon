package com.jdagnogo.welovemarathon.blog.data

import androidx.annotation.Keep

@Keep
data class BlogData(
    val blogDao: BlogDao,
    val blogRemoteData: BlogRemoteData,
    val blogMapper: BlogMapper,
)
