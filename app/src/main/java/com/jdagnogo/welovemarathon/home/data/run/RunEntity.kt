package com.jdagnogo.welovemarathon.home.data.run

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.home.domain.MarathonRun
@Keep
@Entity(tableName = "run_entity")
data class RunEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val date: String = "",
    val image: String = "",
    val link: String = "",
) {
    fun toMarathonRun(): MarathonRun {
        return MarathonRun(
            id = id,
            title = title,
            date = date,
            image = image,
            link = link,
        )
    }
}
