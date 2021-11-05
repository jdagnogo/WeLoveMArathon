package com.jdagnogo.welovemarathon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

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
    { parseConfigCustomPalette(White, false) }
}

val LightColorCustomPalette: @Composable () -> WLMColors by lazy {
    { parseConfigCustomPalette(Black, true) }
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
