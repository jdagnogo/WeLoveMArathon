package com.jdagnogo.welovemarathon.beach.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.beach.data.BeachEntity
import com.jdagnogo.welovemarathon.common.category.LongCategoryItem
import com.jdagnogo.welovemarathon.common.type_two.TypeTwoItem

@Keep
data class Beach(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val location: String = "",
    val locationLink: String = "",
    val image: String = "",
) {
    fun toBeachEntity(): BeachEntity {
        return BeachEntity(
            id = id,
            name = name,
            description = description,
            locationLink = locationLink,
            location = location,
            image = image
        )
    }

    fun toLongCategoryItem(): LongCategoryItem {
        return LongCategoryItem(
            id = id,
            name = name,
            locationLink = locationLink,
            image = image,
        )
    }

    fun toTypeTwoItem(): TypeTwoItem {
        return TypeTwoItem(
            name = name,
            description = description,
            locationLink = locationLink,
            location = location,
            image = image
        )
    }
}
