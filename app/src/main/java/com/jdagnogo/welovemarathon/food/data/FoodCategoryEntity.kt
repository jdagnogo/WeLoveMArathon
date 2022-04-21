package com.jdagnogo.welovemarathon.food.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.food.data.FoodCategoryEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.food.domain.FoodCategory

@Keep
@Entity(tableName = TABLE)
data class FoodCategoryEntity(
    @PrimaryKey val name: String = "",
    val icon: String = "",
    val ordinal: Int = 0,
) {
    fun toDomainCategory(): FoodCategory {
        return FoodCategory(
            name, icon, ordinal
        )
    }

    companion object {
        const val TABLE = "FoodCategory"
    }
}
