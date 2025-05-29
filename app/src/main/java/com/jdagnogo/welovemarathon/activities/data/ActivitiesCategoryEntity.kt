package com.jdagnogo.welovemarathon.activities.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.activities.data.ActivitiesCategoryEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.activities.domain.ActivitiesCategory

@Keep
@Entity(tableName = TABLE)
data class ActivitiesCategoryEntity(
    @PrimaryKey val name: String = "",
    val icon: String = "",
    val ordinal: Int = 0,
) {
    fun toDomainCategory(): ActivitiesCategory {
        return ActivitiesCategory(
            name, icon, ordinal
        )
    }

    companion object {
        const val TABLE = "ActivitiesCategory"
    }
}
