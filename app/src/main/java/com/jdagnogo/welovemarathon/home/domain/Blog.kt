package com.jdagnogo.welovemarathon.home.domain

import com.jdagnogo.welovemarathon.home.data.blog.BlogEntity

data class Blog(
    val id: String = "",
    val title: String = "",
    val summary: String = "",
    val author: String = "",
    val link: String = "",
    val date: String = "",
    val image: String = "",
    val authorImage: String = "",
) {
    fun toBlogEntity(): BlogEntity {
        return BlogEntity(
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
}

fun Blog.fakeList(): List<Blog> {
    return listOf(Blog("title1",
        "title1",
        summary = "grjkgkjghkehgkeghkrghkerjg",
        author = "toto",
        date = "18/11/21"),
        Blog("title2",
            "title2",
            summary = "grjkgkjghkehgkeghkrghkerjggrjkgkjghkehgkeghkrghkerjggrjkgkjghkehgkeghkrghkerjg",
            author = "toto",
            date = "18 septembre 2021"),
        Blog("title3", "title3", summary = "grjkgkjghkehgkeghkrghkerjg"),
        Blog("title4", "title4", summary = "grjkgkjghkehgkeghkrghkerjg"),
        Blog("title5", "title4", summary = "grjkgkjghkehgkeghkrghkerjg"),
        Blog("title6", "title4", summary = "grjkgkjghkehgkeghkrghkerjg"),
        Blog("title7", "title4", summary = "grjkgkjghkehgkeghkrghkerjg"),
        Blog("title8", "title4", summary = "grjkgkjghkehgkeghkrghkerjg"),
        Blog("title5", "title5", summary = "grjkgkjghkehgkeghkrghkerjg"))
}
