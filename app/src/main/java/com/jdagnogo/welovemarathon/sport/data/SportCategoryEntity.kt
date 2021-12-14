package com.jdagnogo.welovemarathon.sport.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.sport.data.SportCategoryEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.sport.domain.SportCategory

@Keep
@Entity(tableName = TABLE)
data class SportCategoryEntity(
    @PrimaryKey val name: String = "",
    val icon: String = "",
    val ordinal: Int = 0,
) {
    fun toSportCategory(): SportCategory {
        return SportCategory(
            name, icon, ordinal
        )
    }

    companion object {
        const val TABLE = "SportCategory"
    }
}
