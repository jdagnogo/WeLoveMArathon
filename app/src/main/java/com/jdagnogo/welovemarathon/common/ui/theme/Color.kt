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
val SecondaryDark = Color(0xFF153754)
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
val EventsColor = Color(0xFF4ECDC4)

// Event categories - mirrors the Google Calendar color the owner picks when
// creating an event, from Calendar's expanded 24-color picker (functions/src/categoryMap.ts
// is the source of truth on the backend side; these hex values must stay in sync with that file).
val EventTheatersColor = Color(0xFFD06B64) // Flamingo
val EventFestivalsColor = Color(0xFFFFAD46) // Mango
val EventSportsColor = Color(0xFF7BD148) // Pistachio
val EventMusicColor = Color(0xFFB3DC6C) // Avocado
val EventCultureColor = Color(0xFFFBE983) // Citron
val EventCharityColor = Color(0xFFB99AFF) // Wisteria
val EventCinemaColor = Color(0xFFC2C2C2) // Graphite
val EventTalksColor = Color(0xFFCABDBF) // Birch

// No category picked (colorId unset, or not one of the 8 mapped swatches) -
// a fixed blue rather than a color pulled from anywhere else.
val EventUncategorizedColor = Color(0xFF3F51B5) // Blueberry

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
