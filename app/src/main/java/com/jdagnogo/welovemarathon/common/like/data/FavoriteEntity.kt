package com.jdagnogo.welovemarathon.common.like.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.common.like.data.FavoriteEntity.Companion.TABLE

@Keep
@Entity(tableName = TABLE)
data class FavoriteEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val locationLink: String = "",
    val number: String = "",
) {
    companion object {
        const val TABLE = "favorite"
    }
}