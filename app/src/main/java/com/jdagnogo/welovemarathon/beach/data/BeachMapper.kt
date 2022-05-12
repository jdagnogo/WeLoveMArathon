package com.jdagnogo.welovemarathon.beach.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.beach.domain.Beach

@Keep
class BeachMapper {
    fun toBeaches(blogEntities: List<BeachEntity>): List<Beach> {
        return blogEntities.map {
            it.toBeach()
        }
    }

    fun toBeachEntities(blogs: List<Beach>): List<BeachEntity> {
        return blogs.map {
            it.toBeachEntity()
        }
    }
}
