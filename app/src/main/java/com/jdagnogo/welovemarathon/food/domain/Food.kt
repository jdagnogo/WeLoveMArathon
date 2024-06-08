package com.jdagnogo.welovemarathon.food.domain

import androidx.annotation.Keep
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.beach.domain.BeachBar
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails
import com.jdagnogo.welovemarathon.common.domain.EntityStringList
import com.jdagnogo.welovemarathon.food.data.FoodEntity
import com.jdagnogo.welovemarathon.map.domain.MapItem

@Keep
data class Food(
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
    fun toFoodEntity(): FoodEntity {
        return FoodEntity(
            id = id,
            name = name,
            website = website,
            location = location,
            locationLink = locationLink,
            number = number,
            description = description,
            images = EntityStringList(images),
            bigImages = EntityStringList(bigImages),
            isRecommended = isRecommended,
            category = category,
            tags = tags,
            longitude = coordinate?.longitude ?: 0.0,
            latitude = coordinate?.latitude ?: 0.0,
            isBeachBar = isBeachBar,
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

    fun toBeachBar(): BeachBar {
        return BeachBar(
            id = id,
            name = name,
            website = website,
            location = location,
            locationLink = locationLink,
            number = number,
            description = description,
            images = images,
            bigImages = bigImages,
            isRecommended = isRecommended,
            category = category,
            tags = tags,
            coordinate = coordinate
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
            isFavItem = isFavItem,
            number = number,
            tags = tags,
            parentIcon = R.drawable.food
        )
    }
}

