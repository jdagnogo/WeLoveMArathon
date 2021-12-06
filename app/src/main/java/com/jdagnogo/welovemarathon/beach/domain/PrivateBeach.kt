package com.jdagnogo.welovemarathon.beach.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.SimpleListItem

@Keep
data class PrivateBeach(
    val id: String = "",
    val name: String = "",
    val parentId: String = "",
    val location: String = "",
    val locationLink: String = "",
    val number: String = "",
){
    fun toSimpleListItem(): SimpleListItem {
        return SimpleListItem(
            id, name, location , number
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
