package com.jdagnogo.welovemarathon.run.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.run.data.RunEntity
@Keep
data class Run(
    val id: String = "",
    val title: String = "",
    val date: String = "",
    val image: String = "",
    val link: String = "",
) {
    fun toRunEntity(): RunEntity {
        return RunEntity(
            id = id,
            title = title,
            date = date,
            image = image,
            link = link,
        )
    }
}

fun Run.fakeList(): List<Run> {
    return listOf(
        Run("toto", "title", "date", "", "link"),
        Run("titi", "title2", "date", "", "link"),
        Run("tata", "title3", "date", "", "link"),
    )
}
