package com.jdagnogo.welovemarathon.shopping.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.shopping.data.ShoppingTagEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingTag

@Keep
@Entity(tableName = TABLE)
data class ShoppingTagEntity(
    @PrimaryKey val name: String = "",
    val category: String = "",
) {
    fun toDomainCategory(): ShoppingTag {
        return ShoppingTag(
            name,
            category
        )
    }

    companion object {
        const val TABLE = "ShoppingTag"
    }
}
