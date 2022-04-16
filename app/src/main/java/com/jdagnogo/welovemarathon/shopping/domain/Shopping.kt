package com.jdagnogo.welovemarathon.shopping.domain

import androidx.annotation.Keep
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails
import com.jdagnogo.welovemarathon.map.domain.MapChip
import com.jdagnogo.welovemarathon.map.domain.MapItem
import com.jdagnogo.welovemarathon.shopping.data.ShoppingEntity

@Keep
data class Shopping(
    var id: String = "",
    var name: String = "",
    var website: String = "",
    var location: String = "",
    var locationLink: String = "",
    var number: String = "",
    var description: String = "",
    var image: String = "",
    @field:JvmField var isRecommended: Boolean = false,
    var category: String = "",
    var tags: String = "",
    var coordinate: GeoPoint? = null,
) {
    fun toShoppingEntity(): ShoppingEntity {
        return ShoppingEntity(
            id = id,
            name = name,
            website = website,
            location = location,
            locationLink = locationLink,
            number = number,
            description = description,
            image = image,
            isRecommended = isRecommended,
            category = category,
            tags = tags,
            longitude = coordinate?.longitude ?: 0.0,
            latitude = coordinate?.latitude ?: 0.0,
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

fun Shopping.map(data: Map<String, Any>) {
    id = data["id"] as? String ?: ""
    name = data["name"] as? String ?: ""
    website = data["website"] as? String ?: ""
    location = data["location"] as? String ?: ""
    locationLink = data["locationLink"] as? String ?: ""
    number = data["number"] as? String ?: ""
    description = data["description"] as? String ?: ""
    image = data["image"] as? String ?: ""
    isRecommended = data["isRecommended"] as? Boolean ?: false
    category = data["category"] as? String ?: ""
}

fun Shopping.fakeList(): List<Shopping> {
    return listOf(
        Shopping(
            "toto",
            "toto",
            category = ShoppingCategories.Woman.name,
            description = "description",
            isRecommended = true
        ),
        Shopping("toto4", "toto", category = ShoppingCategories.Woman.name),
        Shopping("toto2", "toto", category = ShoppingCategories.Woman.name),
        Shopping("toto3", "toto", category = ShoppingCategories.Woman.name)
    )
}
