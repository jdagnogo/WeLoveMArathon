package com.jdagnogo.welovemarathon.map.domain

import androidx.annotation.Keep

@Keep
data class MapItem(
    val name: String = "",
    val image: String = "",
    val tags: String = "",
)
