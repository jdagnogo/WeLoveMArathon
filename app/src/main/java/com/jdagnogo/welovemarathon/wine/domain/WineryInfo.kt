package com.jdagnogo.welovemarathon.wine.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.wine.data.WineryInfoEntity

@Keep
data class WineryInfo(
    val id: String = "",
    val title: String = "Zeginis Winery",
    val description: String = "",
    val wines: List<Wine> = emptyList(),
    val usefulInfos: String = "",
    val location: String = "",
    val locationLink: String = "",
    val tourInfos: String = "",
    val wineMoreInfo: String = "",
    val moreInfo: String = "",
    val images: List<String> = emptyList(),
    val bigImages: List<String> = emptyList(),
) {
    fun toWineryInfoEntity(): WineryInfoEntity {
        return WineryInfoEntity(
            id = id,
            title = title,
            description = description,
            wines = wines,
            usefulInfos = usefulInfos,
            location = location,
            wineMoreInfo = wineMoreInfo,
            moreInfo = moreInfo,
            tourInfos = tourInfos,
            locationLink = locationLink,
            images = images,
            bigImages = bigImages,
        )
    }
}