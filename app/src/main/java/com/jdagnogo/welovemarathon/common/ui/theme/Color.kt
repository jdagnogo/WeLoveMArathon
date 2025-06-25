package com.jdagnogo.welovemarathon.common.ui.theme

import androidx.compose.foundation.background
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

val Primary = Color(0xFFFAFAFA)
val PrimaryLight = Color(0xFFFFFFFF)
val PrimaryDark = Color(0xFFE0E0E0)
val Secondary = Color(0xFF1E4F7B)
val SecondaryLight = Color(0xFF1E4F7B)
val SecondaryDark = Color(0xFF1E4F7B)
val Black = Color(0xFF000000)
val White = Color(0xFFFAFAFA)
val BackgroundLight = Primary
val ContentBackgroundLight = Color(0xFFF0F0F0)
val ContentBackgroundDark = Color(0xFF161616)
val ActivityColor = Color(0xFF93DB9B)
val BeachColor = Color(0xFFFAAF3F)
val CultureColor = Color(0xFF94CDFD)
val ShoppingColor = Color(0xFFA584FA)
val FoodColor = Color(0xFFE7CD4E)
val WineColor = Color(0xFFFF9EA5)
val TagColor = Color(0xFF94CDFD)

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
