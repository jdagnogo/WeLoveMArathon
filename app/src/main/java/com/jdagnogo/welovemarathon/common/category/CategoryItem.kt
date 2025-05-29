package com.jdagnogo.welovemarathon.common.category

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.like.domain.Favorite

@Keep
data class CategoryItem(
    val id: String = "",
    val name: String = "",
    val isFavItem: Boolean = false,
    val locationLink: String = "",
    val number: String = "",
    val tags: String = "",
    val parentIcon: Int = 0,

) {
    fun toFavorite(): Favorite {
        return Favorite(
            id = id,
            name = name,
            locationLink = locationLink,
            number = number,
            tags = tags,
            parentIcon = parentIcon
        )
    }
    fun toFakeCategoryItemList() = listOf(
        CategoryItem("id", "name", tags = "#toto #titi"),
        CategoryItem("id2", "name", tags = "#toto #titi"),
        CategoryItem("id3", "name", tags = "#toto #titi"),
        CategoryItem("id4", "name", tags = "#toto #titi"),
        CategoryItem("id5", "name", tags = "#toto #titi"),
    )
}
