package com.jdagnogo.welovemarathon.restaurant.domain

import androidx.annotation.Keep
import com.google.firebase.firestore.GeoPoint
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails
import com.jdagnogo.welovemarathon.common.domain.EntityStringList
import com.jdagnogo.welovemarathon.restaurant.data.AmenitiesList
import com.jdagnogo.welovemarathon.restaurant.data.PlatesList
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantEntity
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantServiceList

@Keep
data class Restaurant(
    val id: String = "",
    val name: String = "",
    val website: String = "",
    val location: String = "",
    val priceRange: String = "",
    val locationLink: String = "",
    var coordinate: GeoPoint? = null,
    val number: String = "",
    val description: String = "",
    val isRecommended: Boolean = false,
    val handicapAccess: Boolean = false,
    val services: List<RestaurantService> = emptyList(),
    val plates: List<Plates> = emptyList(),
    val amenities: List<Amenities> = emptyList(),
    val images: List<String> = emptyList(),
    val menu: List<String> = emptyList(),
    val cuisines: List<String> = emptyList(),
    val categories: List<String> = emptyList(),
    val drinks: List<String> = emptyList(),
    val bigImages: List<String> = emptyList(),
) {
    fun toRestaurantEntity(): RestaurantEntity {
        return RestaurantEntity(
            id = id,
            name = name,
            website = website,
            location = location,
            locationLink = locationLink,
            longitude = coordinate?.longitude ?: 0.0,
            latitude = coordinate?.latitude ?: 0.0,
            number = number,
            handicapAccess = handicapAccess,
            cuisines = EntityStringList(cuisines),
            categories = EntityStringList(categories),
            drinks = EntityStringList(drinks),
            menu = EntityStringList(menu),
            priceRange = priceRange,
            description = description,
            isRecommended = isRecommended,
            services = RestaurantServiceList(services),
            plates = PlatesList(plates),
            amenities = AmenitiesList(amenities),
            images = EntityStringList(images),
            bigImages = EntityStringList(bigImages)
        )
    }

    fun toRecommendedCategoryDetails(): RecommendedCategoryDetails {
        return RecommendedCategoryDetails(
            id = id,
            name = name,
            website = website,
            location = location,
            locationLink = locationLink,
            number = number,
            description = description,
            images = images,
            bigImages = bigImages,
            tags = ""
        )
    }
}