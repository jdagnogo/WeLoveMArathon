package com.jdagnogo.welovemarathon.common.category

data class CategoryTag(
    val name: String = "",
    var isSelected: Boolean,
)

val CategoryTagFake = CategoryTag(
    name = "name",
    isSelected = false,
)

