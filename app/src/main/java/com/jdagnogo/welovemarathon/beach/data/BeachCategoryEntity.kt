package com.jdagnogo.welovemarathon.beach.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.beach.data.BeachCategoryEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.beach.domain.BeachCategory

@Keep
@Entity(tableName = TABLE)
data class BeachCategoryEntity(
    @PrimaryKey val name: String = "",
    val icon: String = "",
    val ordinal: Int = 0,
) {
    fun toDomainCategory(): BeachCategory {
        return BeachCategory(
            name, icon, ordinal
        )
    }

    companion object {
        const val TABLE = "BeachCategory"
    }
}
