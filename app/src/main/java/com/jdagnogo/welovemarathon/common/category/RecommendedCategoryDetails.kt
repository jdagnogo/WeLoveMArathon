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
    val images: List<String> = emptyList(),
    val bigImages: List<String> = emptyList(),
    val tags: String = "",
)

val RecommendedCategoryDetailsFake = RecommendedCategoryDetails(
    name = "name",
    images = listOf(""),
    description = "gre fkejr ferfk fr jkjkfe ef kj refer jk fre ",
    number = "69853652265",
    tags = "#rgrgr #fre #rgr #regre",
    bigImages = emptyList(),
)
