package com.jdagnogo.welovemarathon.common.submenu

import androidx.compose.ui.graphics.Color
import com.jdagnogo.welovemarathon.common.banner.GifBanner

data class SubMenuUiModel(
    val screenName: String,
    val items: List<SubMenuItem>,
    val image: Int,
    val background: Color,
    val banner: GifBanner? = null,
)
