package com.jdagnogo.welovemarathon.shopping.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.SimpleListItem
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
    var isRecommended: Boolean = false,
    var category: ShoppingCategories = ShoppingCategories.Woman,
) {
    fun toShoppingEntity(): ShoppingEntity {
        return ShoppingEntity(id,
            name,
            website,
            location,
            locationLink,
            number,
            description,
            image,
            isRecommended,
            category = category.name)
    }

    fun toSimpleListItem(): SimpleListItem {
        return SimpleListItem(
            id, name, location, number
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
    val categoryAsString = data["category"] as? String ?: ShoppingCategories.Woman.name
    category = ShoppingCategories.valueOf(categoryAsString)
}

fun Shopping.fakeList(): List<Shopping> {
    return listOf(
        Shopping("toto",
            "toto",
            category = ShoppingCategories.Woman,
            description = "description",
            isRecommended = true),
        Shopping("toto4", "toto", category = ShoppingCategories.Woman),
        Shopping("toto2", "toto", category = ShoppingCategories.Woman),
        Shopping("toto3", "toto", category = ShoppingCategories.Woman)
    )
}
