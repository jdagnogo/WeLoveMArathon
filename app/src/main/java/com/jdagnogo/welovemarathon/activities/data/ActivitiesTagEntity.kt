package com.jdagnogo.welovemarathon.activities.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.activities.data.ActivitiesTagEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.activities.domain.ActivitiesTag

@Keep
@Entity(tableName = TABLE)
data class ActivitiesTagEntity(
    @PrimaryKey val name: String = "",
    val category: String = "",
) {
    fun toDomainCategory(): ActivitiesTag {
        return ActivitiesTag(
            name,
            category
        )
    }

    companion object {
        const val TABLE = "ActivitiesTag"
    }
}
