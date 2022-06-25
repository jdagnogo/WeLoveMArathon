package com.jdagnogo.welovemarathon.wine.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.wine.domain.WineSocial

@Keep
@Entity(tableName = WineSocialEntity.TABLE)
data class WineSocialEntity(
    @PrimaryKey var id: String = "",
    val name: String,
    val icon: String,
    val link: String,
    val ordinal: Int = 0,

    ) {
    fun toWineSocial(): WineSocial {
        return WineSocial(
            id = id,
            name = name,
            icon = icon,
            link = link,
            ordinal = ordinal
        )
    }

    companion object {
        const val TABLE = "WineSocial"
    }
}