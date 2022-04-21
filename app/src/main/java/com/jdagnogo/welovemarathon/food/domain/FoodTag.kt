package com.jdagnogo.welovemarathon.food.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.food.data.FoodTagEntity
@Keep
data class FoodTag(
    val name: String = "",
    val category: String = ""
){
    fun toEntity(): FoodTagEntity {
        return FoodTagEntity(
            name,
            category
        )
    }
}
