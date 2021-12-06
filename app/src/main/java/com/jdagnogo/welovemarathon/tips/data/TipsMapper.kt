package com.jdagnogo.welovemarathon.tips.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.tips.domain.Tips

@Keep
class TipsMapper {
    fun toTips(entities: List<TipsEntity>): List<Tips> {
        return entities.map {
            it.toTips()
        }
    }

    fun toTipsEntities(tips: List<Tips>): List<TipsEntity> {
        return tips.map {
            it.toTipsEntity()
        }
    }
}
