package com.jdagnogo.welovemarathon.wine.domain

import com.jdagnogo.welovemarathon.wine.data.WineTourEntity

data class WineTour(
    val id: String = "",
    val name: String = "",
    val icon: String = "",
    val images: List<String> = emptyList(),
    val description: String = "",
    val hour: String = "",
    val pack: String = "",
    val link: String = "",
    val ordinal: Int = 0,
) {
    fun toWineTourEntity(): WineTourEntity {
        return WineTourEntity(
            id = id,
            name = name,
            icon = icon,
            images = images,
            link = link,
            pack = pack,
            description = description,
            hour = hour,
            ordinal = ordinal
        )
    }
}