package com.jdagnogo.welovemarathon.common.category

import androidx.annotation.Keep

@Keep
data class CategoryItem(
    val id: String = "",
    val name: String = "",
    val locationLink: String = "",
    val number: String = "",
    val tags: String = "",
)
