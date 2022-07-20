package com.jdagnogo.welovemarathon.about.domain

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Member(
    val name: String = "",
    val icon: String = "",
    val ordinal: Int = 0,
) : Serializable