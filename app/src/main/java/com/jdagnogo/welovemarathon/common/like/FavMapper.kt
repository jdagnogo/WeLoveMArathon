package com.jdagnogo.welovemarathon.common.like

import com.jdagnogo.welovemarathon.common.like.data.FavoriteEntity
import com.jdagnogo.welovemarathon.common.like.domain.Favorite

class FavMapper {
    fun toEntity(favorite: Favorite): FavoriteEntity {
        with(favorite) {
            return FavoriteEntity(
                id = id,
                name = name,
                locationLink = locationLink,
                number = number,
                parentIcon = parentIcon,
                tags = tags
            )
        }
    }

    fun toDomain(favorite: List<FavoriteEntity>): List<Favorite> {
       return favorite.map {
            Favorite(
                id = it.id,
                name = it.name,
                locationLink = it.locationLink,
                number = it.number,
                parentIcon = it.parentIcon,
                tags = it.tags
            )
        }
    }
}