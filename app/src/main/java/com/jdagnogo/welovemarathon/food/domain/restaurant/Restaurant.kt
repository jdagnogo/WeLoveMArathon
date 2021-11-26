package com.jdagnogo.welovemarathon.food.domain.restaurant

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantEntity
@Keep
data class Restaurant(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val number: String = "",
    val location: String = "",
    val locationLink: String = "",
    val place: String = "",
    val website: String = "",
    val isFavorite: Boolean = false,
) {
    fun toRestaurantEntity(): RestaurantEntity {
        return RestaurantEntity(
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
}

fun Restaurant.fakeList(): List<Restaurant> {
    return listOf(
        Restaurant("toto1 jfnejfnj rkjn rgjgn jerg",
            "restaurant1 frnfn nfnerfbk jfb,r n,erfb, be ",
            "number",
            "number grg rg ger r",
            "location rege rg gr erg ger ", isFavorite = true),
        Restaurant("toto2", "restaurant2", "number", "location"),
        Restaurant("toto3", "restaurant3", "number", "location"),
        Restaurant("toto4", "restaurant4", "number", "location"),
    )
}
