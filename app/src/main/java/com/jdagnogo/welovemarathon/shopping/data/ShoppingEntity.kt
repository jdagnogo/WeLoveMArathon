package com.jdagnogo.welovemarathon.shopping.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.GeoPoint
import com.jdagnogo.welovemarathon.common.domain.ImageList
import com.jdagnogo.welovemarathon.shopping.data.ShoppingEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.shopping.domain.Shopping

@Keep
@Entity(tableName = TABLE)
data class ShoppingEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val website: String = "",
    val location: String = "",
    val locationLink: String = "",
    val number: String = "",
    val description: String = "",
    val image: String = "",
    val isRecommended: Boolean = false,
    val category: String = "",
    var images: ImageList,
    var bigImages: ImageList,
    var tags: String = "",
    var longitude: Double = 0.0,
    var latitude: Double = 0.0,
) {
    fun toShopping(): Shopping {
        return Shopping(
            id = id,
            name = name,
            website = website,
            location = location,
            locationLink = locationLink,
            number = number,
            description = description,
            images = images.images,
            bigImages = bigImages.images,
            isRecommended = isRecommended,
            category = category,
            tags = tags,
            coordinate = GeoPoint(latitude, longitude)
        )
    }

    companion object {
        const val TABLE = "Shopping"
    }
}
