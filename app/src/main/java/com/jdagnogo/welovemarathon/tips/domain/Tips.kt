package com.jdagnogo.welovemarathon.tips.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.tips.data.TipsEntity

@Keep
data class Tips(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var imageDescription: String = "",
    var image: String = "",
) {
    fun toTipsEntity(): TipsEntity {
        return TipsEntity(
            id, title, description, imageDescription, image
        )
    }
}

fun Tips.toFakeList(): List<Tips> {
    return listOf(
        Tips("toto1", "toto", "description", "image"),
        Tips("toto2", "toto", "description", "image"),
        Tips("toto3", "toto", "description", "image"),
        Tips("toto4", "toto", "description", "image"),
    )
}
