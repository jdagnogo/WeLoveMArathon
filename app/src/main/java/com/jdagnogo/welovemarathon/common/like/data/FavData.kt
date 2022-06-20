package com.jdagnogo.welovemarathon.common.like.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.like.FavMapper

@Keep
data class FavData(
    val dao: FavDao,
    val mapper: FavMapper
)