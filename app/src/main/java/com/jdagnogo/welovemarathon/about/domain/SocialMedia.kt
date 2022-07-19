package com.jdagnogo.welovemarathon.about.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.about.data.SocialMediaEntity

@Keep
data class SocialMedia(
    val icon: String,
    val link: String,
    val ordinal: Int = 0,
) {
    fun toSocialMediaEntity(): SocialMediaEntity {
        return SocialMediaEntity(
            icon = icon,
            link = link,
            ordinal = ordinal
        )
    }
}