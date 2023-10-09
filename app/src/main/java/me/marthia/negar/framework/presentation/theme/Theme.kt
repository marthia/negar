package me.marthia.negar.framework.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat


@VisibleForTesting
private val LightDefaultColorScheme = lightColorScheme(
    primary = md_theme_light_primary_tone_800,
    onPrimary = md_theme_light_primary_tone_100,
    primaryContainer = md_theme_light_primary_tone_300,
    onPrimaryContainer = md_theme_light_primary_tone_900,
    inversePrimary = md_theme_light_primary_tone_400,
    secondary = md_theme_light_secondary_tone_800,
    onSecondary = md_theme_light_secondary_tone_200,
    secondaryContainer = md_theme_light_secondary_tone_300,
    onSecondaryContainer = md_theme_light_secondary_tone_900,
    tertiary = Blue40,
    onTertiary = Color.White,
    tertiaryContainer = Blue90,
    onTertiaryContainer = Blue10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = md_theme_light_primary_tone_50,
    onBackground = md_theme_light_primary_tone_900,
    surface = md_theme_light_primary_tone_50,
    onSurface = md_theme_light_primary_tone_900,
    surfaceVariant = md_theme_light_primary_tone_100,
    onSurfaceVariant = md_theme_light_primary_tone_300,
    inverseSurface = DarkPurpleGray20,
    inverseOnSurface = DarkPurpleGray95,
    outline = md_theme_light_primary_tone_900,
)

/**
 * Dark default theme color scheme
 */
@VisibleForTesting
private val DarkDefaultColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = Purple20,
    primaryContainer = Purple30,
    onPrimaryContainer = Purple90,
    secondary = Orange80,
    onSecondary = Orange20,
    secondaryContainer = Orange30,
    onSecondaryContainer = Orange90,
    tertiary = Blue80,
    onTertiary = Blue20,
    tertiaryContainer = Blue30,
    onTertiaryContainer = Blue90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = DarkPurpleGray10,
    onBackground = DarkPurpleGray90,
    surface = DarkPurpleGray10,
    onSurface = DarkPurpleGray90,
    surfaceVariant = PurpleGray30,
    onSurfaceVariant = PurpleGray80,
    inverseSurface = DarkPurpleGray90,
    inverseOnSurface = DarkPurpleGray10,
    outline = PurpleGray60,
)

/**
 * Light Android gradient colors
 */
val LightAndroidGradientColors = GradientColors(container = DarkGreenGray95)

/**
 * Dark Android gradient colors
 */
val DarkAndroidGradientColors = GradientColors(container = Color.Black)

/**
 * Light Android background theme
 */
val LightAndroidBackgroundTheme = BackgroundTheme(color = DarkGreenGray95)

/**
 * Dark Android background theme
 */
val DarkAndroidBackgroundTheme = BackgroundTheme(color = Color.Black)

/**
 * Now in Android theme.
 *
 * @param darkTheme Whether the theme should use a dark color scheme (follows system by default).
 * @param isDynamicTheming If `true`, enable the use of dynamic theming
 *
 */

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    // Color scheme
    val colorScheme = when {
        isDynamicTheming && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> if (darkTheme) DarkDefaultColorScheme else LightDefaultColorScheme
    }
    // Gradient colors
    val emptyGradientColors = GradientColors(container = colorScheme.surfaceColorAtElevation(2.dp))
    val defaultGradientColors = GradientColors(
        top = colorScheme.inverseOnSurface,
        bottom = colorScheme.primaryContainer,
        container = colorScheme.surface,
    )

    // Background theme
    val defaultBackgroundTheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = 2.dp,
    )

    val tintTheme = when {
        isDynamicTheming && supportsDynamicTheming() -> TintTheme(colorScheme.primary)
        else -> TintTheme()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    // Composition locals
    CompositionLocalProvider(
        LocalGradientColors provides defaultGradientColors,
        LocalBackgroundTheme provides defaultBackgroundTheme,
        LocalTintTheme provides tintTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content,
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S