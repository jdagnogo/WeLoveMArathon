package com.jdagnogo.welovemarathon.beach.domain

import androidx.annotation.Keep

@Keep
data class PrivateBeach(
    val id: String = "",
    val name: String = "",
    val parentId: String = "",
    val location: String = "",
    val locationLink: String = "",
    val phone: String = "",
)

fun PrivateBeach.toFakeList(): List<PrivateBeach> {
    return listOf(
        PrivateBeach(id = "toto", name = "name1"),
        PrivateBeach(id = "toto2", name = "name2"),
        PrivateBeach(id = "toto3", name = "name3")
    )
}
