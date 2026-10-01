package com.danielhc.showcase.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = InkNavy,
    onPrimary = PaperLight,
    secondary = ClayLight,
    onSecondary = PaperLight,
    background = PaperLight,
    onBackground = InkNavy,
    surface = PaperLight,
    onSurface = InkNavy,
    surfaceVariant = PaperLight,
    onSurfaceVariant = GraphiteLight,
    outline = GraphiteLight,
    outlineVariant = GraphiteLight,
    error = ClayLight,
    onError = PaperLight,
)

private val DarkColors = darkColorScheme(
    primary = TextOnDark,
    onPrimary = PaperDark,
    secondary = ClayDark,
    onSecondary = PaperDark,
    background = PaperDark,
    onBackground = TextOnDark,
    surface = PaperDark,
    onSurface = TextOnDark,
    surfaceVariant = PaperDark,
    onSurfaceVariant = GraphiteDark,
    outline = GraphiteDark,
    outlineVariant = GraphiteDark,
    error = ClayDark,
    onError = PaperDark,
)

/**
 * Always uses this app's hand-authored "blueprint" palette — deliberately not Material You
 * dynamic color, which would replace the intentional identity with a wallpaper-derived one.
 */
@Composable
fun DanielShowcaseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = DanielShowcaseTypography,
        content = content,
    )
}
