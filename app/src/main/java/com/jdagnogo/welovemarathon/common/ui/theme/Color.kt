package com.jdagnogo.welovemarathon.common.ui.theme

import androidx.compose.foundation.background
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

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
val BackgroundLight = Secondary
val ContentBackgroundLight = Color(0xFFF1F0F0)
val ContentBackgroundDark = Color(0xFF161616)

class WLMColors(
    val gradient: List<Color>,
    val gradientVariant: List<Color>,
    val bottomBarIndicator: Color,
    val bottomBarIconSelected: Color,
    val bottomBarIcon: Color,
    val isLight: Boolean,
    val divider: Color,
    val contentBackground: Color,
)

@Composable
fun parseConfigCustomPalette(
    gradient: List<Color>,
    gradientVariant: List<Color>,
    bottomBarIndicator: Color,
    divider: Color,
    isLight: Boolean,
    contentBackground : Color
): WLMColors {
    return WLMColors(
        gradient = gradient,
        gradientVariant = gradientVariant,
        bottomBarIndicator = bottomBarIndicator,
        bottomBarIconSelected = bottomBarIndicator,
        bottomBarIcon = SecondaryDark,
        isLight = isLight,
        divider = divider,
        contentBackground = contentBackground
    )
}

@Composable
fun parseConfigPalette(isLight: Boolean): Colors {
    return Colors(
        primary = Primary,
        primaryVariant = PrimaryDark,
        secondary = Secondary,
        secondaryVariant = SecondaryDark,
        background = BackgroundLight,
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

fun Modifier.offsetGradientBackground(
    colors: List<Color>,
    width: Float,
    offset: Float = 0f,
) = background(
    Brush.horizontalGradient(
        colors,
        startX = -offset,
        endX = width - offset,
        tileMode = TileMode.Mirror
    )
)