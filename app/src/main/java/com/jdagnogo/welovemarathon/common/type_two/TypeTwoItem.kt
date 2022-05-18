package com.jdagnogo.welovemarathon.common.type_two

import androidx.annotation.Keep

@Keep
data class TypeTwoItem(
    val name: String = "",
    val location: String = "",
    val locationLink: String = "",
    val description: String = "",
    val image: String = "",
)