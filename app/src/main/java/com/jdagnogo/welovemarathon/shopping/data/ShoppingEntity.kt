package com.jdagnogo.welovemarathon.shopping.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.shopping.data.ShoppingEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategories
@Keep
@Entity(tableName = TABLE)
data class ShoppingEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val website: String = "",
    val location: String = "",
    val locationLink: String = "",
    val number: String = "",
    val description: String = "",
    val image: String = "",
    val isRecommended: Boolean = false,
    val category: String = ShoppingCategories.Woman.name,
) {
    fun toShopping(): Shopping {
        return Shopping(
            id, name, website, location, locationLink, number, description, image, isRecommended,
            category = try {
                ShoppingCategories.valueOf(category)
            } catch (e: Exception) {
                ShoppingCategories.Woman
            }
        )
    }

    companion object {
        const val TABLE = "Shopping"
    }
}

