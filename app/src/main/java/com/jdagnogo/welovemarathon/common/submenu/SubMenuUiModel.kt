package com.jdagnogo.welovemarathon.common.submenu

import androidx.annotation.Keep
import androidx.compose.ui.graphics.Color
import com.jdagnogo.welovemarathon.common.banner.GifBanner
@Keep
data class SubMenuUiModel(
    val screenName: String,
    val items: List<SubMenuItem>,
    val image: Int,
    val backgroundColor: Color,
    val banner: GifBanner? = null,
)
