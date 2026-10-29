package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ObsidianColorScheme = darkColorScheme(
    primary = KineticNeon,
    onPrimary = OnKineticNeon,
    primaryContainer = KineticNeonContainer,
    onPrimaryContainer = OnKineticNeonContainer,
    inversePrimary = KineticNeonLight,
    secondary = KineticNeonLight,
    onSecondary = OnKineticNeon,
    secondaryContainer = SurfaceContainerHigh,
    onSecondaryContainer = TextPrimary,
    tertiary = MetricCyan,
    onTertiary = OnMetricCyan,
    tertiaryContainer = MetricCyanContainer,
    onTertiaryContainer = OnMetricCyan,
    background = ObsidianBg,
    onBackground = TextPrimary,
    surface = ObsidianSurface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceContainerHigh,
    onSurfaceVariant = TextSecondary,
    surfaceTint = KineticNeon,
    outline = BorderHighlight,
    outlineVariant = BorderSubtle,
    error = CriticalRed,
    onError = Color.Black,
    errorContainer = CriticalRedContainer,
    onErrorContainer = Color.White
)

@Composable
fun RepForgeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ObsidianColorScheme,
        typography = Typography,
        content = content
    )
}

// Alias for backwards compatibility
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    RepForgeTheme(content = content)
}
