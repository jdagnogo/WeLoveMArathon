package com.jdagnogo.welovemarathon.culture.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.culture.domain.Culture

@Keep
class CultureMapper {
    fun toCultureCategories(blogEntities: List<CultureEntity>): List<Culture> {
        return blogEntities.map {
            it.toCulture()
        }
    }

    fun toCultureEntities(blogs: List<Culture>): List<CultureEntity> {
        return blogs.map {
            it.toCultureEntity()
        }
    }
}
