package com.jdagnogo.welovemarathon.beach.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.beach.domain.PrivateBeach

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

    fun toPrivateBeaches(privateBeachEntity: List<PrivateBeachEntity>): List<PrivateBeach> {
        return privateBeachEntity.map {
            it.toPrivateBeaches()
        }
    }

    fun toPrivateBeachEntities(privateBeach: List<PrivateBeach>): List<PrivateBeachEntity> {
        return privateBeach.map {
            it.toPrivateBeachEntity()
        }
    }
}
