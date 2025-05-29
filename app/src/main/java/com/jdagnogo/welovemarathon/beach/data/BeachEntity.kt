package com.jdagnogo.welovemarathon.beach.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.beach.data.BeachEntity.Companion.TABLE_BEACH
import com.jdagnogo.welovemarathon.beach.domain.Beach

@Keep
@Entity(tableName = TABLE_BEACH)
class BeachEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val description: String = "",
    val location: String = "",
    val locationLink: String = "",
    val image: String = "",
    val ordinal: Int = 0,
) {
    fun toBeach(): Beach {
        return Beach(
            id = id,
            name = name,
            description = description,
            locationLink = locationLink,
            location = location,
            image = image,
            ordinal = ordinal
        )
    }

    companion object {
        const val TABLE_BEACH = "beach"
    }
}

