package com.jdagnogo.welovemarathon.beach.domain


import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.beach.data.BeachTagEntity

@Keep
data class BeachTag(
    val name: String = "",
    val category: String = ""
) {
    fun toEntity(): BeachTagEntity {
        return BeachTagEntity(
            name,
            category
        )
    }
}
