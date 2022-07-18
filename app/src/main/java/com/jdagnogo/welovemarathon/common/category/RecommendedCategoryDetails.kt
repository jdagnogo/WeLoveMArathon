package com.jdagnogo.welovemarathon.common.category

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.ui.component.HorizontalCarouselItem

@Keep
data class RecommendedCategoryDetails(
    val id: String = "",
    val name: String = "",
    val website: String = "",
    val location: String = "",
    val locationLink: String = "",
    val number: String = "",
    val description: String = "",
    val images: List<HorizontalCarouselItem> = emptyList(),
    val tags: String = "",
)

val RecommendedCategoryDetailsFake = RecommendedCategoryDetails(
    name = "name",
    images = listOf(HorizontalCarouselItem("","")),
    description = "gre fkejr ferfk fr jkjkfe ef kj refer jk fre ",
    number = "69853652265",
    tags = "#rgrgr #fre #rgr #regre"
)
