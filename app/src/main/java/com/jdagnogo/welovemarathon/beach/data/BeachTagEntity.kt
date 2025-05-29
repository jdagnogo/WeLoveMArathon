package com.jdagnogo.welovemarathon.beach.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.beach.data.BeachTagEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.beach.domain.BeachTag

@Keep
@Entity(tableName = TABLE)
data class BeachTagEntity(
    @PrimaryKey val name: String = "",
    val category: String = "",
) {
    fun toDomainCategory(): BeachTag {
        return BeachTag(
            name,
            category
        )
    }

    companion object {
        const val TABLE = "BeachTag"
    }
}
