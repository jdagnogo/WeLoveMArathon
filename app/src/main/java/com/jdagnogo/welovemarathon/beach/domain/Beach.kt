package com.jdagnogo.welovemarathon.beach.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.beach.data.BeachEntity

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
}

fun Beach.toFakeList(): List<Beach> {
    return listOf(
        Beach(id = "toto", name = "name1"),
        Beach(id = "toto2", name = "name2"),
        Beach(id = "toto3", name = "name3")
    )
}
