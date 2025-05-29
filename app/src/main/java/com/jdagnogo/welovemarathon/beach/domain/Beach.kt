package com.jdagnogo.welovemarathon.beach.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.beach.data.BeachEntity
import com.jdagnogo.welovemarathon.common.category.LongCategoryItem
import com.jdagnogo.welovemarathon.common.type_two.TypeTwoItem
import com.jdagnogo.welovemarathon.map.domain.MapChip

@Keep
data class Beach(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val location: String = "",
    val locationLink: String = "",
    val image: String = "",
    val ordinal: Int = 0,
) {
    fun toBeachEntity(): BeachEntity {
        return BeachEntity(
            id = id,
            name = name,
            description = description,
            locationLink = locationLink,
            location = location,
            image = image,
            ordinal = ordinal,
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

    fun toMapChip(): MapChip {
        return MapChip(
            name = name,
            key = name,
        )
    }
}
