package com.jdagnogo.welovemarathon.activities.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.activities.data.ActivitiesTagEntity
@Keep
data class ActivitiesTag(
    val name: String = "",
    val category: String = ""
){
    fun toEntity(): ActivitiesTagEntity {
        return ActivitiesTagEntity(
            name,
            category
        )
    }
}
