package com.jdagnogo.welovemarathon.activities.domain

import androidx.annotation.Keep
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.activities.data.ActivitiesEntity
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails
import com.jdagnogo.welovemarathon.common.domain.ImageList
import com.jdagnogo.welovemarathon.map.domain.MapItem

@Keep
data class Activities(
    var id: String = "",
    var name: String = "",
    var website: String = "",
    var location: String = "",
    var locationLink: String = "",
    var number: String = "",
    var description: String = "",
    var images: List<String> = emptyList(),
    var bigImages: List<String> = emptyList(),
    @field:JvmField var isRecommended: Boolean = false,
    var category: String = "",
    var tags: String = "",
    var coordinate: GeoPoint? = null,
    @field:JvmField val isBeachBar: Boolean = false,
    val parent: String = "",
) {
    fun toActivitiesEntity(): ActivitiesEntity {
        return ActivitiesEntity(
            id = id,
            name = name,
            website = website,
            location = location,
            locationLink = locationLink,
            number = number,
            description = description,
            isRecommended = isRecommended,
            category = category,
            tags = tags,
            longitude = coordinate?.longitude ?: 0.0,
            latitude = coordinate?.latitude ?: 0.0,
            isBeachBar = isBeachBar,
            images = ImageList( images),
            bigImages = ImageList(bigImages),
            parent = parent
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

    fun toCategoryItem(isFavItem: Boolean): CategoryItem {
        return CategoryItem(
            id = id,
            name = name,
            locationLink = locationLink,
            number = number,
            isFavItem = isFavItem,
            tags = tags,
            parentIcon = R.drawable.ic_activity,
        )
    }
}

