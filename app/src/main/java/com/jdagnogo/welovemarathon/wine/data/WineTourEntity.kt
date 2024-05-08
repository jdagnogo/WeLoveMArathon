package com.jdagnogo.welovemarathon.wine.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.common.domain.ImageList
import com.jdagnogo.welovemarathon.wine.domain.WineTour

@Keep
@Entity(tableName = WineTourEntity.TABLE)
data class WineTourEntity(
    @PrimaryKey val id: String,
    val name: String,
    val icon: String,
    var images: ImageList,
    val description: String,
    val pack: String,
    val link: String,
    val hour: String,
    val ordinal: Int = 0,
) {
    fun toWineTour(): WineTour {
        return WineTour(
            id = id,
            name = name,
            icon = icon,
            pack = pack,
            link = link,
            images = images.images,
            description = description,
            hour = hour,
            ordinal = ordinal
        )
    }

    companion object {
        const val TABLE = "WineTour"
    }
}