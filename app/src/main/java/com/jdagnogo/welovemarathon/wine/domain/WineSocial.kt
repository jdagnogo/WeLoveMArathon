package com.jdagnogo.welovemarathon.wine.domain

import com.jdagnogo.welovemarathon.wine.data.WineSocialEntity

data class WineSocial(
    val id: String = "",
    val name: String = "",
    val icon: String = "",
    val link: String = "",
    val ordinal: Int = 0,
) {
    fun toWineSocialEntity(): WineSocialEntity {
        return WineSocialEntity(
            id = id,
            name = name,
            icon = icon,
            link = link,
            ordinal = ordinal
        )
    }
}