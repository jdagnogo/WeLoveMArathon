package com.jdagnogo.welovemarathon.common.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

val DrawableId = SemanticsPropertyKey<Int>("drawableID")
var SemanticsPropertyReceiver.drawableID by DrawableId

val BackgroundColor = SemanticsPropertyKey<Color>("backgroundColor")
var SemanticsPropertyReceiver.backgroundColor by BackgroundColor

fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        modifier.invoke(this)
    } else {
        this
    }
}
