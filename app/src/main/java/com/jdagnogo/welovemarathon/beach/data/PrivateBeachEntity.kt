package com.jdagnogo.welovemarathon.beach.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.beach.data.PrivateBeachEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.beach.domain.PrivateBeach

@Keep
@Entity(tableName = TABLE)
data class PrivateBeachEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val parentId: String = "",
    val location: String = "",
    val locationLink: String = "",
    val number: String = "",
) {
    fun toPrivateBeaches(): PrivateBeach {
        return PrivateBeach(id, name, parentId, location, locationLink, number)
    }

    companion object {
        const val TABLE = "private_beaches"
    }
}
