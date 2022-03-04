package com.jdagnogo.welovemarathon.common.category

import androidx.annotation.Keep

@Keep
data class RecommendedCategoryDetails(
    val id: String = "",
    val name: String = "",
    val website: String = "",
    val location: String = "",
    val locationLink: String = "",
    val number: String = "",
    val description: String = "",
    val image: String = "",
    val tags: String = "",
)
