package com.jdagnogo.welovemarathon.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun WeLoveMarathonTheme(
    content: @Composable () -> Unit
) {
    val colors = LightColorPalette()

    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = Primary
        )
        sysUiController.setNavigationBarColor(
            color = Color.White
        )
    }
    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

private val DarkColorPalette = darkColors(
    primary = Primary,
    primaryVariant = PrimaryDark,
    secondary = Secondary
)

val LightColorPalette: @Composable () -> Colors by lazy {
    { parseConfigPalette(true) }
}

val DarkColorCustomPalette: @Composable () -> WLMColors by lazy {
    {
        parseConfigCustomPalette(
            gradient = listOf(Primary, White, PrimaryDark, White),
            gradientVariant = listOf(Primary, White, PrimaryDark, White),
            White, PrimaryDark, contentBackground = ContentBackgroundDark, isLight = false
        )
    }
}

val LightColorCustomPalette: @Composable () -> WLMColors by lazy {
    {
        parseConfigCustomPalette(
            gradient = listOf(Primary, White, PrimaryDark, White),
            gradientVariant = listOf(Primary, White, PrimaryDark, White),
            Black, PrimaryDark, contentBackground = ContentBackgroundLight, isLight = true
        )
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
