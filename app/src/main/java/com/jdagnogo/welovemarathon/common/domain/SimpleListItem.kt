package com.jdagnogo.welovemarathon.common.domain

import androidx.annotation.Keep

@Keep
data class SimpleListItem(
    val id: String = "",
    var name: String = "",
    var location: String = "",
    var locationLink: String = "",
    var number: String = "",
)

fun SimpleListItem.fakeList(): List<SimpleListItem>{
    return listOf(
        SimpleListItem()
    )
}
