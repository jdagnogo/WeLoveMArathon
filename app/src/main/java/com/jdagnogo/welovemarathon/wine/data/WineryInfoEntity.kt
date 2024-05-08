package com.jdagnogo.welovemarathon.wine.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.common.domain.ImageList
import com.jdagnogo.welovemarathon.wine.domain.WineryInfo
import com.jdagnogo.welovemarathon.wine.domain.Wines

@Keep
@Entity(tableName = WineryInfoEntity.TABLE)
data class WineryInfoEntity(
    @PrimaryKey val id: String,
    val title: String = "Zeginis Winery",
    val description: String,
    val wines: Wines,
    val usefulInfos: String,
    val location: String,
    val locationLink: String,
    val wineMoreInfo: String = "",
    val moreInfo: String = "",
    val tourInfos: String = "",
    var images: ImageList,
    var bigImages: ImageList,
) {

    fun toWineryInfo(): WineryInfo {
        return WineryInfo(
            id = id,
            title = title,
            description = description,
            wines = wines.wines,
            usefulInfos = usefulInfos,
            location = location,
            tourInfos = tourInfos,
            moreInfo = moreInfo,
            wineMoreInfo = wineMoreInfo,
            locationLink = locationLink,
            images = images.images,
            bigImages = bigImages.images,
        )
    }

    companion object {
        const val TABLE = "WineryInfo"
    }
}
