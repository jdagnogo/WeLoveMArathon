package com.jdagnogo.welovemarathon.about.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.about.domain.SocialMedia

@Keep
@Entity(tableName = SocialMediaEntity.TABLE)
data class SocialMediaEntity(
    @PrimaryKey val icon: String,
    val link: String,
    val ordinal: Int = 0,
) {
    fun toSocialMedia(): SocialMedia {
        return SocialMedia(
            icon = icon,
            link = link,
            ordinal = ordinal
        )
    }

    companion object {
        const val TABLE = "SocialMedia"
    }
}