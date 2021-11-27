package com.jdagnogo.welovemarathon.blog.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.blog.data.BlogEntity.Companion.TABLE_BLOG
import com.jdagnogo.welovemarathon.blog.domain.Blog

@Keep
@Entity(tableName = TABLE_BLOG)
data class BlogEntity(
    @PrimaryKey val id: String = "",
    val title: String = "",
    val summary: String = "",
    val author: String = "",
    val link: String = "",
    val date: String = "",
    val image: String = "",
    val authorImage: String = "",
) {
    fun toBlog(): Blog {
        return Blog(
            id = id,
            title = title,
            summary = summary,
            author = author,
            link = link,
            date = date,
            image = image,
            authorImage = authorImage
        )
    }

    companion object {
        const val TABLE_BLOG = "blogs"
    }
}
