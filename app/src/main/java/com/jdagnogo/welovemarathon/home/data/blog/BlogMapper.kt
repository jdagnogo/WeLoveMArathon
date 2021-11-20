package com.jdagnogo.welovemarathon.home.data.blog

import com.jdagnogo.welovemarathon.home.domain.Blog
import javax.inject.Inject

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
