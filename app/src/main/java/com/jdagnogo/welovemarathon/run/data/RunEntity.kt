package com.jdagnogo.welovemarathon.run.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.run.domain.Run

@Keep
@Entity(tableName = "run_entity")
data class RunEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val date: String = "",
    val image: String = "",
    val link: String = "",
) {
    fun toMarathonRun(): Run {
        return Run(
            id = id,
            title = title,
            date = date,
            image = image,
            link = link,
        )
    }
}
