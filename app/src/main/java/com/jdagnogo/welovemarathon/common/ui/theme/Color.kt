package com.jdagnogo.welovemarathon.common.ui.theme

import androidx.compose.foundation.background
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
val SystemBarColors = Color(0xFFFFFFFF)
val BottomMenuBackground = Color(0xFFFFFFFF)
val Transparent = Color(0xff0080)
val Primary = Color(0xFF3D4962)
val PrimaryLight = Color(0xFF697490)
val PrimaryDark = Color(0xFF142238)
val Secondary = Color(0xFFFF9EA5)
val SecondaryLight = Color(0xFFFFD0D6)
val SecondaryDark = Color(0xFFCA6E76)
val Black = Color(0xFF000000)
val White = Color(0xFFFAFAFA)
val Neutral4 = Color(0xFF4C6599)
val Neutral3 = Color(0x80070707)
val Background = Color(0xE1E1E1)
val BackgroundLight = Primary
val ContentBackgroundLight = Primary
val ContentBackgroundDark = Color(0xFF161616)
val ActivityColor = Color(0xFF93DB9B)
val BeachColor = Color(0xFFFAAF3F)
val CultureColor = Color(0xFF94CDFD)
val ShoppingColor = Color(0xFFA584FA)
val FoodColor = Color(0xFFE7CD4E)
val WineColor = Color(0xFFFF9EA5)
val TagColor = Color(0xFF94CDFD)
val TipsColor = Primary

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
        bottomBarIcon = SecondaryLight,
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
        secondaryVariant = SecondaryLight,
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
