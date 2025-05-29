package com.jdagnogo.welovemarathon.sport.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.sport.domain.Sport

@Keep
@Entity(tableName = SportEntity.TABLE)
data class SportEntity(
    @PrimaryKey var id: String = "",
    var name: String = "",
    var website: String = "",
    var location: String = "",
    var locationLink: String = "",
    var number: String = "",
    var category: String = "",
) {
    fun toSport(): Sport {
        return Sport(
            id, name, website, location, locationLink, number, category
        )
    }

    companion object {
        const val TABLE = "Sport"
    }
}
