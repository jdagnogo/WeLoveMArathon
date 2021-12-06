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
    var imageDescription: String = "",
    var image: String = "",
) {
    fun toTips(): Tips {
        return Tips(
            id, title, description, imageDescription, image
        )
    }

    companion object {
        const val TABLE = "tips"
    }
}

