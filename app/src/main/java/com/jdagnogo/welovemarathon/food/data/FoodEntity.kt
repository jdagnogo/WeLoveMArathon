package com.jdagnogo.welovemarathon.food.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.GeoPoint
import com.jdagnogo.welovemarathon.food.data.FoodEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.food.domain.Food

@Keep
@Entity(tableName = TABLE)
data class FoodEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val website: String = "",
    val location: String = "",
    val locationLink: String = "",
    val number: String = "",
    val description: String = "",
    var images: List<String> = emptyList(),
    var bigImages: List<String> = emptyList(),
    val category: String = "",
    val isRecommended: Boolean = false,
    var tags: String = "",
    var longitude: Double = 0.0,
    var latitude: Double = 0.0,
    val isBeachBar: Boolean = false,
    val parent: String = "",
) {
    fun toFood(): Food {
        return Food(
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
            coordinate = GeoPoint(latitude, longitude),
            isBeachBar = isBeachBar,
            parent = parent
        )
    }

    companion object {
        const val TABLE = "food"
    }
}
