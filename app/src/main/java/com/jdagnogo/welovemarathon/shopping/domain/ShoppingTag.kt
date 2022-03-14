package com.jdagnogo.welovemarathon.shopping.domain

import com.jdagnogo.welovemarathon.shopping.data.ShoppingTagEntity

data class ShoppingTag(
    val name: String = ""
){
    fun toEntity(): ShoppingTagEntity {
        return ShoppingTagEntity(
            name,
        )
    }
}
