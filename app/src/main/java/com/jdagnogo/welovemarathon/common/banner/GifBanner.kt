package com.jdagnogo.welovemarathon.common.banner

import androidx.annotation.Keep

@Keep
data class GifBanner(
    val id: String = "",
    val name: String = "",
    val resLink: String = "",
    val type: String = "",
    val link: String = "",
) {
    fun toGifEntity(): GifBannerEntity {
        return GifBannerEntity(
            id = id,
            name = name,
            resLink = resLink,
            type = type,
            link = link
        )
    }
}

const val HOME = "Home"
const val SHOPPING = "Shopping"

fun GifBanner.fakeBanner(): GifBanner {
    return GifBanner(
        id = "fakeID",
        resLink = "https://static.wixstatic.com/media/0018f2_95a6cefa5ef44209943c333efba714ea~mv2.gif",
    )
}
