package com.jdagnogo.welovemarathon.shopping.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.shopping.data.ShoppingTagEntity
@Keep
data class ShoppingTag(
    val name: String = ""
){
    fun toEntity(): ShoppingTagEntity {
        return ShoppingTagEntity(
            name,
        )
    }
}
