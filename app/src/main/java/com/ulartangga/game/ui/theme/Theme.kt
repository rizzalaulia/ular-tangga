package com.ulartangga.game.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val KidsColorScheme = lightColorScheme(
    primary = BlueSky,
    secondary = ButtonBlue,
    surface = LightGray,
    background = BlueSky,
    onPrimary = TextDark,
    onSecondary = White,
    onBackground = TextDark,
    onSurface = TextDark
)

@Composable
fun UlarTanggaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KidsColorScheme,
        typography = Typography,
        content = content
    )
}
