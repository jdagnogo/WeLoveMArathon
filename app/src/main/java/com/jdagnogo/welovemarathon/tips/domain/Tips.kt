package com.jdagnogo.welovemarathon.tips.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.submenu.SubMenuItem
import com.jdagnogo.welovemarathon.tips.data.TipsEntity

@Keep
data class Tips(
    val id: String = "",
    val title: String = "",
    var description: String = "",
    val ordinal: Int = 0,
    val image: String = "",
    val icon: String = "",
) {
    fun toTipsEntity(): TipsEntity {
        return TipsEntity(
            id, title, description, image = image, icon = icon
        )
    }

    fun toSubMenuItem(): SubMenuItem {
        return SubMenuItem(
            title = title,
            iconUrl = icon,
            ordinal = ordinal
        )
    }
}
