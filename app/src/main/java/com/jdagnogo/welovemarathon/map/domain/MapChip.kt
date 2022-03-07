package com.jdagnogo.welovemarathon.map.domain

import androidx.annotation.Keep

@Keep
data class MapChip(
    val id: String,
    val iconUrl: String,
    val name: String
)
