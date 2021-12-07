package com.jdagnogo.welovemarathon.food.data.restaurant

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.food.domain.restaurant.Food

@Keep
@Entity(tableName = "restaurant")
data class FoodEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val image: String = "",
    val number: String = "",
    val location: String = "",
    val locationLink: String = "",
    val place: String = "",
    val website: String = "",
    @field:JvmField val isRecommended: Boolean = false,
    val type: String = "",
) {
    fun toFood(): Food {
        return Food(
            id = id,
            name = name,
            image = image,
            number = number,
            location = location,
            locationLink = locationLink,
            place = place,
            website = website,
            isRecommended = isRecommended,
            type = type
        )
    }

    companion object {
        const val TABLE_RESTAURANT = "restaurant"
    }
}
