package com.jdagnogo.welovemarathon.home.data

import com.jdagnogo.welovemarathon.home.data.blog.BlogDao
import com.jdagnogo.welovemarathon.home.data.blog.BlogMapper
import com.jdagnogo.welovemarathon.home.data.blog.BlogRemoteData
import com.jdagnogo.welovemarathon.home.data.run.RunDao
import com.jdagnogo.welovemarathon.home.data.run.RunMapper
import com.jdagnogo.welovemarathon.home.data.run.RunRemoteData

data class HomeData(
    val blogDao: BlogDao,
    val blogRemoteData: BlogRemoteData,
    val blogMapper: BlogMapper,
    val runDao: RunDao,
    val runRemoteData: RunRemoteData,
    val runMapper: RunMapper,
)
