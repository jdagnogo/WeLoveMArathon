package com.jdagnogo.welovemarathon.culture.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.category.LongCategoryItem
import com.jdagnogo.welovemarathon.common.type_two.TypeTwoItem
import com.jdagnogo.welovemarathon.culture.data.CultureEntity

@Keep
data class Culture(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val location: String = "",
    val locationLink: String = "",
    val image: String = "",
    val phone: String = "",
    val website: String = "",
    val ordinal: Int = 0,
) {
    fun toCultureEntity(): CultureEntity {
        return CultureEntity(
            id = id,
            name = name,
            description = description,
            locationLink = locationLink,
            location = location,
            image = image,
            ordinal = ordinal,
            phone = phone,
            website = website,
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
            phone = phone,
            website = website,
            image = image
        )
    }
}
