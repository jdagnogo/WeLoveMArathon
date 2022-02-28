package com.jdagnogo.welovemarathon.shopping.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.shopping.data.ShoppingCategoryEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategory

@Keep
@Entity(tableName = TABLE)
data class ShoppingCategoryEntity(
    @PrimaryKey val name: String = "",
    val icon: String = "",
    val ordinal: Int = 0,
) {
    fun toDomainCategory(): ShoppingCategory {
        return ShoppingCategory(
            name, icon, ordinal
        )
    }

    companion object {
        const val TABLE = "ShoppingCategory"
    }
}
