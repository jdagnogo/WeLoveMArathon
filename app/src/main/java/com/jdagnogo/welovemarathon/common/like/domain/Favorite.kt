package com.jdagnogo.welovemarathon.common.like.domain

import androidx.annotation.Keep

@Keep
data class Favorite(
    val id: String = "",
    val name: String = "",
    val locationLink: String = "",
    val number: String = "",
)