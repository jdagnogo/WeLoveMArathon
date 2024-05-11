package com.jdagnogo.welovemarathon.restaurant.domain

import androidx.annotation.Keep

@Keep
data class Amenities(
    val type: String = "",
    val description: String = "",
    val icon: String = "",
)