package com.jdagnogo.welovemarathon.common.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFF94BCB4)
val PrimaryLight = Color(0xFFC5EFE6)
val PrimaryDark = Color(0xFF658C84)
val Secondary = Color(0xFFE0B3A6)
val SecondaryLight = Color(0xFFFFE5D7)
val SecondaryDark = Color(0xFFAD8377)
val Black = Color(0xFF000000)
val White = Color(0xFFFAFAFA)
val Neutral4 = Color(0x1f000000)
val Neutral3 = Color(0x1fffffff)

class WLMColors(
    val bottomBarIndicator: Color,
    val bottomBarIconSelected: Color,
    val bottomBarIcon: Color,
    val isLight: Boolean,
    val divider: Color
)

@Composable
fun parseConfigCustomPalette(bottomBarIndicator: Color, divider : Color,isLight: Boolean): WLMColors {
    return WLMColors(
        bottomBarIndicator = bottomBarIndicator,
        bottomBarIconSelected = bottomBarIndicator,
        bottomBarIcon = SecondaryDark,
        isLight = isLight,
        divider = divider
    )
}

@Composable
fun parseConfigPalette(isLight: Boolean): Colors {
    return Colors(
        primary = Primary,
        primaryVariant = PrimaryDark,
        secondary = Secondary,
        secondaryVariant = SecondaryDark,
        background = MaterialTheme.colors.background,
        surface = MaterialTheme.colors.surface,
        error = MaterialTheme.colors.error,
        onPrimary = MaterialTheme.colors.onPrimary,
        onSecondary = MaterialTheme.colors.onSecondary,
        onBackground = MaterialTheme.colors.onBackground,
        onSurface = MaterialTheme.colors.onSurface,
        onError = MaterialTheme.colors.onError,
        isLight = isLight,
    )
}
