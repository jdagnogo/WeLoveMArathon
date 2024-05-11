package com.jdagnogo.welovemarathon.restaurant.domain

import androidx.annotation.Keep

@Keep
data class Plates(
    val name: String = "",
    val image: String = "",
    val bigImage: String = "",
)