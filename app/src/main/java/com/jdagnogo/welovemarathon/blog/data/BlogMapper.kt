package com.jdagnogo.welovemarathon.blog.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.blog.domain.Blog
import javax.inject.Inject

@Keep
class BlogMapper @Inject constructor() {
    fun toBlogs(blogEntities: List<BlogEntity>): List<Blog> {
        return blogEntities.map {
            it.toBlog()
        }
    }

    fun toBlogsEntities(blogs: List<Blog>): List<BlogEntity> {
        return blogs.map {
            it.toBlogEntity()
        }
    }
}
