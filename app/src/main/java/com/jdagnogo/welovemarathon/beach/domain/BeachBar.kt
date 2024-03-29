package com.jdagnogo.welovemarathon.beach.domain

import androidx.annotation.Keep
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails
import com.jdagnogo.welovemarathon.map.domain.MapItem

@Keep
data class BeachBar(
    val id: String = "",
    val name: String = "",
    val parentId: String = "",
    val location: String = "",
    val locationLink: String = "",
    var website: String = "",
    var description: String = "",
    var images: List<String> = emptyList(),
    var bigImages: List<String> = emptyList(),
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
            images = images,
            bigImages = bigImages,
            website = website,
            locationLink = locationLink,
            location = location,
            number = number,
            description = description,
            tags = tags,
        )
    }

    fun toCategoryItem(isFavItem: Boolean = false): CategoryItem {
        return CategoryItem(
            id = id,
            name = name,
            locationLink = locationLink,
            number = number,
            isFavItem = isFavItem,
            tags = tags,
            parentIcon = R.drawable.ic_beach,
        )
    }

    fun toMapItem(): MapItem {
        return MapItem(
            name = name,
            tags = tags,
            latLng = LatLng(
                coordinate?.latitude ?: 0.0,
                coordinate?.longitude ?: 0.0,
            )
        )
    }
}
