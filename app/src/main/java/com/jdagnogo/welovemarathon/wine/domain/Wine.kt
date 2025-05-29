package com.jdagnogo.welovemarathon.wine.domain

import androidx.annotation.Keep

@Keep
data class Wine(
    val id : String = "",
    val name: String = "",
    val icon: String = "",
)