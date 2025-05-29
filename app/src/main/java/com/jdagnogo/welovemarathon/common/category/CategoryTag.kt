package com.jdagnogo.welovemarathon.common.category

import androidx.annotation.Keep

@Keep
data class CategoryTag(
    val name: String = "",
    var isSelected: Boolean,
)

val CategoryTagFake = CategoryTag(
    name = "name",
    isSelected = false,
)
