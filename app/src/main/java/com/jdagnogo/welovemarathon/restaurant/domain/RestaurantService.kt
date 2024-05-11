package com.jdagnogo.welovemarathon.restaurant.domain

import androidx.annotation.Keep

@Keep
data class RestaurantService(
    val title: String = "",
    val icon: String = "",
    val description: String = "",
)