package com.jdagnogo.welovemarathon.food.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.food.data.FoodTagEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.food.domain.FoodTag

@Keep
@Entity(tableName = TABLE)
data class FoodTagEntity(
    @PrimaryKey val name: String = "",
    val category: String = "",
) {
    fun toDomainCategory(): FoodTag {
        return FoodTag(
            name,
            category
        )
    }

    companion object {
        const val TABLE = "FoodTag"
    }
}
