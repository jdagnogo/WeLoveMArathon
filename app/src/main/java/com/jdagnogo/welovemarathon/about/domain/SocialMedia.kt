package com.jdagnogo.welovemarathon.about.domain

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class SocialMedia(
    val icon: String = "",
    val link: String = "",
    val ordinal: Int = 0,
) : Serializable