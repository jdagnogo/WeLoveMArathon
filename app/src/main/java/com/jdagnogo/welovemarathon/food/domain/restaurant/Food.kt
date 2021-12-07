package com.jdagnogo.welovemarathon.food.domain.restaurant

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.SimpleListItem
import com.jdagnogo.welovemarathon.food.data.restaurant.FoodEntity

@Keep
data class Food(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val number: String = "",
    val location: String = "",
    val locationLink: String = "",
    val place: String = "",
    val website: String = "",
    @field:JvmField var isRecommended: Boolean = false,
    var type: String = "",
) {
    fun foodEntity(): FoodEntity {
        return FoodEntity(
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

    fun toSimpleListItem(): SimpleListItem {
        return SimpleListItem(
            id, name, location, number
        )
    }
}

fun Food.fakeList(): List<Food> {
    return listOf(
        Food("toto1 jfnejfnj rkjn rgjgn jerg",
            "restaurant1 frnfn nfnerfbk jfb,r n,erfb, be ",
            "number",
            "number grg rg ger r",
            "location rege rg gr erg ger ", isRecommended = true),
        Food("toto2", "restaurant2", "number", "location"),
        Food("toto3", "restaurant3", "number", "location"),
        Food("toto4", "restaurant4", "number", "location"),
    )
}
