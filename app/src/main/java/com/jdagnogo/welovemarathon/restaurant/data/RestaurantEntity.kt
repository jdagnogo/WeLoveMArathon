package com.jdagnogo.welovemarathon.restaurant.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.GeoPoint
import com.jdagnogo.welovemarathon.common.domain.EntityStringList
import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant

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
    var priceRange: String = "",
    val description: String = "",
    val isRecommended: Boolean = false,
    val handicapAccess: Boolean = false,
    val evCharger: Boolean = false,
    val services: RestaurantServiceList,
    val plates: PlatesList,
    val amenities: AmenitiesList,
    var images: EntityStringList,
    var drinks: EntityStringList,
    var menu: EntityStringList,
    var cuisines: EntityStringList,
    var categories: EntityStringList,
    var bigImages: EntityStringList,
) {

    fun toRestaurant() : Restaurant{
        return Restaurant(
            id = id,
            name = name,
            website = website,
            location = location,
            locationLink = locationLink,
            categories = categories.data,
            priceRange = priceRange,
            cuisines = cuisines.data,
            drinks = drinks.data,
            menu = menu.data,
            evCharger = evCharger,
            handicapAccess = handicapAccess,
            coordinate = GeoPoint(latitude, longitude),
            number = number,
            description = description,
            isRecommended = isRecommended,
            services = services.restaurantService,
            plates = plates.plates,
            amenities = amenities.amenities,
            images = images.data,
            bigImages = bigImages.data

        )
    }

    companion object {
        const val TABLE = "restaurant"
    }
}