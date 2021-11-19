package com.jdagnogo.welovemarathon.home.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.home.domain.Blog

@Entity(tableName = "blog_entity")
data class BlogEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val summary: String = "",
    val author: String = "",
    val link: String = "",
    val date: String = "",
) {
    fun toBlog(): Blog {
        return Blog(
            id = id,
            title = title,
            summary = summary,
            author = author,
            link = link,
            date = date
        )
    }
}
