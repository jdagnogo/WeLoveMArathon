package com.jdagnogo.welovemarathon.sport.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.sport.data.SportEntity

@Keep
data class Sport(
    var id: String = "",
    var name: String = "",
    var website: String = "",
    var location: String = "",
    var locationLink: String = "",
    var number: String = "",
    var category: String = "",
) {
    fun toSportEntity(): SportEntity {
        return SportEntity(
            id, name, website, locationLink, locationLink, number, category
        )
    }
}
