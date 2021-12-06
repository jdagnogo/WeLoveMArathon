package com.jdagnogo.welovemarathon.common

import androidx.annotation.Keep

@Keep
data class SimpleListItem(
    val id: String = "",
    var name: String = "",
    var location: String = "",
    var number: String = "",
)

fun SimpleListItem.fakeList(): List<SimpleListItem>{
    return listOf(
        SimpleListItem()
    )
}
