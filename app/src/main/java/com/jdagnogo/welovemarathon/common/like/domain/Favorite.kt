package com.jdagnogo.welovemarathon.common.like.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.category.CategoryItem

@Keep
data class Favorite(
    val id: String = "",
    val name: String = "",
    val locationLink: String = "",
    val number: String = "",
){
    fun toCategoryItem(isFavItem: Boolean): CategoryItem {
        return CategoryItem(
            id = id,
            name = name,
            locationLink = locationLink,
            number = number,
            isFavItem = isFavItem,
            tags = ""
        )
    }
}