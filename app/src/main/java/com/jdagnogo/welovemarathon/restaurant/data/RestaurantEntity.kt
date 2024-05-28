package com.jdagnogo.welovemarathon.restaurant.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.GeoPoint
import com.jdagnogo.welovemarathon.common.domain.ImageList
import com.jdagnogo.welovemarathon.restaurant.domain.Amenities
import com.jdagnogo.welovemarathon.restaurant.domain.Plates
import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantService
@Keep
@Entity(tableName = RestaurantEntity.TABLE)
data class RestaurantEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val website: String = "",
    val location: String = "",
    val locationLink: String = "",
    var longitude: Double = 0.0,
    var latitude: Double = 0.0,
    val number: String = "",
    val description: String = "",
    val isRecommended: Boolean = false,
    val services: RestaurantServiceList,
    val plates: PlatesList,
    val amenities: AmenitiesList,
    var images: ImageList,
    var bigImages: ImageList,
) {

    fun toRestaurant() : Restaurant{
        return Restaurant(
            id = id,
            name = name,
            website = website,
            location = location,
            locationLink = locationLink,
            coordinate = GeoPoint(latitude, longitude),
            number = number,
            description = description,
            isRecommended = isRecommended,
            services = services.restaurantService,
            plates = plates.plates,
            amenities = amenities.amenities,
            images = images.images,
            bigImages = bigImages.images

        )
    }

    companion object {
        const val TABLE = "restaurant"
    }
}