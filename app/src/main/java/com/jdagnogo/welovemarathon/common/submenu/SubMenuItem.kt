package com.jdagnogo.welovemarathon.common.submenu

import androidx.annotation.Keep

@Keep
data class SubMenuItem(
    val title: String = "",
    val subtitle: String = "",
    val icon: Int = 0,
    val ordinal: Int = 0,
    val iconUrl: String = ""
)
