package com.jdagnogo.welovemarathon.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun WeLoveMarathonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit,
) {

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette()
    }

    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = colors.background
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

private val DarkColorPalette = darkColors(
    primary = Primary,
    primaryVariant = PrimaryDark,
    secondary = Secondary
)

val LightColorPalette: @Composable () -> Colors by lazy {
    { parseConfigPalette(true) }
}

// custom palette colors
val DarkColorCustomPalette: @Composable () -> WLMColors by lazy {
    {
        parseConfigCustomPalette(
            gradient = listOf(Primary, White, PrimaryDark, White),
            gradientVariant = listOf(Primary, White, PrimaryDark, White),
            White, Neutral3, contentBackground = ContentBackgroundDark, isLight = false)
    }
}

val LightColorCustomPalette: @Composable () -> WLMColors by lazy {
    {
        parseConfigCustomPalette(
            gradient = listOf(Primary, White, PrimaryDark, White),
            gradientVariant = listOf(Primary, White, PrimaryDark, White),
            Black, Neutral4, contentBackground = ContentBackgroundLight, isLight = true)
    }
}

object WeLoveMarathonTheme {
    val colors: WLMColors
        @Composable
        get() {
            return if (isSystemInDarkTheme()) {
                DarkColorCustomPalette()
            } else {
                LightColorCustomPalette()
            }
        }
}