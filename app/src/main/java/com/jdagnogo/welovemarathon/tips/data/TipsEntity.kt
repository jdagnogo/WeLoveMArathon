package com.jdagnogo.welovemarathon.tips.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.tips.data.TipsEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.tips.domain.Tips

@Keep
@Entity(tableName = TABLE)
data class TipsEntity(
    @PrimaryKey var id: String = "",
    var title: String = "",
    var description: String = "",
    var image: String = "",
    val ordinal: Int = 0,
    var icon: String = "",
    var color: String = "",
) {
    fun toTips(): Tips {
        return Tips(
            id, title, description, image = image, icon = icon,
            color = color,
            ordinal = ordinal,
        )
    }

    companion object {
        const val TABLE = "tips"
    }
}

