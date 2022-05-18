package com.jdagnogo.welovemarathon.beach.domain

import androidx.annotation.Keep
import com.google.firebase.firestore.GeoPoint
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails

@Keep
data class BeachBar(
    val id: String = "",
    val name: String = "",
    val parentId: String = "",
    val location: String = "",
    val locationLink: String = "",
    var website: String = "",
    var description: String = "",
    var image: String = "",
    @field:JvmField var isRecommended: Boolean = false,
    var category: String = "",
    var tags: String = "",
    var coordinate: GeoPoint? = null,
    val number: String = "",
) {
    fun toRecommendedCategoryItem(): RecommendedCategoryDetails {
        return RecommendedCategoryDetails(
            id = id,
            name = name,
            image = image,
            website = website,
            locationLink = locationLink,
            location = location,
            number = number,
            description = description,
            tags = tags,
        )
    }

    fun toCategoryItem(): CategoryItem {
        return CategoryItem(
            id = id,
            name = name,
            locationLink = locationLink,
            number = number,
            tags = tags
        )
    }
}
