package com.jdagnogo.welovemarathon.culture.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.culture.data.CultureEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.culture.domain.Culture

@Keep
@Entity(tableName = TABLE)
class CultureEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val description: String = "",
    val location: String = "",
    val locationLink: String = "",
    val image: String = "",
) {
    fun toCulture(): Culture {
        return Culture(
            id = id,
            name = name,
            description = description,
            locationLink = locationLink,
            location = location,
            image = image
        )
    }

    companion object {
        const val TABLE = "Culture"
    }
}

