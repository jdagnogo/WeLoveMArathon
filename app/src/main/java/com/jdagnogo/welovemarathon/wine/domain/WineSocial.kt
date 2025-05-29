package com.jdagnogo.welovemarathon.wine.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.about.domain.SocialMedia
import com.jdagnogo.welovemarathon.wine.data.WineSocialEntity
@Keep
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

    fun toSocialMedia() = SocialMedia(
        icon = icon,
        link = link,
    )

}