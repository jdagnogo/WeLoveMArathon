package com.jdagnogo.welovemarathon.beach.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.beach.data.PrivateBeachEntity
import com.jdagnogo.welovemarathon.common.domain.SimpleListItem


@Keep
data class PrivateBeach(
    val id: String = "",
    val name: String = "",
    val parentId: String = "",
    val location: String = "",
    val locationLink: String = "",
    val number: String = "",
) {
    fun toPrivateBeachEntity(): PrivateBeachEntity {
        return PrivateBeachEntity(id, name, parentId, location, locationLink, number)
    }

    fun toSimpleListItem(): SimpleListItem {
        return SimpleListItem(
            id, name, location, locationLink = locationLink, number
        )
    }
}

fun PrivateBeach.toFakeList(): List<PrivateBeach> {
    return listOf(
        PrivateBeach(id = "toto", name = "name1"),
        PrivateBeach(id = "toto2", name = "name2"),
        PrivateBeach(id = "toto3", name = "name3")
    )
}
