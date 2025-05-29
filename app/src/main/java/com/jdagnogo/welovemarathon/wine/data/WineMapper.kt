package com.jdagnogo.welovemarathon.wine.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.wine.domain.WineSocial
import com.jdagnogo.welovemarathon.wine.domain.WineTour

@Keep
class WineMapper {
    fun toWineTour(entities: List<WineTourEntity>): List<WineTour> {
        return entities.map {
            it.toWineTour()
        }
    }

    fun toWineTourEntities(entities: List<WineTour>): List<WineTourEntity> {
        return entities.map {
            it.toWineTourEntity()
        }
    }

    fun toWineSocial(entities: List<WineSocialEntity>): List<WineSocial> {
        return entities.map {
            it.toWineSocial()
        }
    }

    fun toWineSocialEntities(entities: List<WineSocial>): List<WineSocialEntity> {
        return entities.map {
            it.toWineSocialEntity()
        }
    }
}