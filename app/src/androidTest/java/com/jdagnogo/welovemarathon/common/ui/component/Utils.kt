package com.jdagnogo.welovemarathon.common.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.ui.test.SemanticsMatcher
import com.jdagnogo.welovemarathon.common.utils.DrawableId

fun hasDrawable(@DrawableRes id: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(DrawableId, id)