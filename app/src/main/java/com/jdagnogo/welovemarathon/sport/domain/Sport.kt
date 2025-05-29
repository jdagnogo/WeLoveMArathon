package com.jdagnogo.welovemarathon.sport.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.SimpleListItem
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
            id, name, website, location, locationLink, number, category
        )
    }

    fun toSimpleListItem(): SimpleListItem {
        return SimpleListItem(
            id, name, location, locationLink, number
        )
    }
}

fun Sport.toFakeList(): List<Sport> {
    return listOf(
        Sport("toto", "toto"),
        Sport("titi", "titi"),
        Sport("tata", "tata"),
    )
}
