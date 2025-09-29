package com.example.mandopcmovil.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Using your defined AppPrimary and AppSecondary for a basic light theme
private val LightColors = lightColorScheme(
    primary = AppPrimary,
    onPrimary = AppOnPrimary,
    secondary = AppSecondary,
    onSecondary = AppOnSecondary,
    background = AppBackground,
    surface = AppSurface
    // You can define other colors like tertiary, error, surfaceVariant, etc.
)

// For a dark theme, you'd typically define a different set of colors.
// For now, let's make it similar to light or use some defaults.
private val DarkColors = darkColorScheme(
    primary = Purple80, // Example from M3 defaults
    secondary = PurpleGrey80, // Example
    tertiary = Pink80, // Example
    background = Color(0xFF1C1B1F), // M3 dark background
    surface = Color(0xFF1C1B1F) // M3 dark surface
    // Override with your specific dark theme colors if you have them,
    // otherwise, these are generic dark theme defaults.
    // primary = AppPrimary, // Or use your primary if it works well in dark
    // secondary = AppSecondary,
)

@Composable
fun MandoPcMovilTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb() // Or another color like surface
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // From Type.kt
        content = content
    )
}
