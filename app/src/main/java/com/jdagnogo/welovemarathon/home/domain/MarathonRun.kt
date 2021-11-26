package com.jdagnogo.welovemarathon.home.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.home.data.run.RunEntity
@Keep
data class MarathonRun(
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

fun MarathonRun.fakeList(): List<MarathonRun> {
    return listOf(
        MarathonRun("toto", "title", "date", "", "link"),
        MarathonRun("titi", "title2", "date", "", "link"),
        MarathonRun("tata", "title3", "date", "", "link"),
    )
}
