package com.jdagnogo.welovemarathon.food.data.restaurant

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.food.domain.restaurant.Restaurant

@Entity(tableName = "restaurant")
data class RestaurantEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val image: String = "",
    val number: String = "",
    val location: String = "",
    val locationLink: String = "",
    val place: String = "",
    val website: String = "",
    val isFavorite: Boolean = false,
) {
    fun toRestaurant(): Restaurant {
        return Restaurant(
            id = id,
            name = name,
            image = image,
            number = number,
            location = location,
            locationLink = locationLink,
            place = place,
            website = website,
            isFavorite = isFavorite
        )
    }

    companion object {
        const val TABLE_RESTAURANT = "restaurant"
    }
}
