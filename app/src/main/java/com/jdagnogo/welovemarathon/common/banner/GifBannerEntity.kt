package com.jdagnogo.welovemarathon.common.banner

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.common.banner.GifBannerEntity.Companion.TABLE_BANNER
@Keep
@Entity(tableName = TABLE_BANNER)
data class GifBannerEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val resLink: String = "",
    val link: String = "",
) {
    fun toGifBanner(): GifBanner {
        return GifBanner(
            id = id,
            name = name,
            resLink = resLink,
            link = link
        )
    }

    companion object {
        const val TABLE_BANNER = "banner"
    }
}
