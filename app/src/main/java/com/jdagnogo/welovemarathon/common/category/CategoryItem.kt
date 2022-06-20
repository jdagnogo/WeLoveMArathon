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
) {
    fun toFavorite(): Favorite {
        return Favorite(
            id = id,
            name = name,
            locationLink = locationLink,
            number = number,
        )
    }
}
