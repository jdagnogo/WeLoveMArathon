package com.jdagnogo.welovemarathon.common.category

import androidx.annotation.Keep

@Keep
data class LongCategoryItem(
    val id: String = "",
    val name: String = "",
    val locationLink: String = "",
    val image: String = "",
)
