package com.jdagnogo.welovemarathon.map.domain

import androidx.annotation.Keep

@Keep
data class MapChip(
    val name: String = "",
    val key: String? = null,
)
