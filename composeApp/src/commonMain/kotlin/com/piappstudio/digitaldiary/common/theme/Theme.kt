package com.piappstudio.digitaldiary.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Digital Diary Theme
 *
 * Provides comprehensive theming support with:
 * - Dark mode detection and override
 * - 6 catching template colors
 * - Complete typography system
 * - User preference support (when dark mode is not forced)
 */

/**
 * Light color scheme for Digital Diary
 */
private fun getLightColorScheme() = lightColorScheme(
    primary = LightColors.Primary,
    onPrimary = LightColors.OnPrimary,
    primaryContainer = LightColors.PrimaryVariant,
    onPrimaryContainer = LightColors.OnPrimary,

    secondary = LightColors.Secondary,
    onSecondary = LightColors.OnSecondary,
    secondaryContainer = LightColors.SecondaryVariant,
    onSecondaryContainer = LightColors.OnSecondary,

    tertiary = LightColors.TemplateColor1,
    onTertiary = LightColors.OnPrimary,
    tertiaryContainer = LightColors.TemplateColor1.copy(alpha = 0.2f),
    onTertiaryContainer = LightColors.TemplateColor1,

    background = LightColors.Background,
    onBackground = LightColors.OnBackground,

    surface = LightColors.Surface,
    onSurface = LightColors.OnSurface,
    surfaceVariant = LightColors.SurfaceVariant,
    onSurfaceVariant = LightColors.OnSurfaceVariant,

    error = LightColors.Error,
    onError = LightColors.OnPrimary,
    errorContainer = LightColors.ErrorContainer,
    onErrorContainer = LightColors.Error,

    outline = LightColors.Outline,
    outlineVariant = LightColors.OutlineVariant,
    scrim = LightColors.Scrim
)

/**
 * Dark color scheme for Digital Diary
 */
private fun getDarkColorScheme() = darkColorScheme(
    primary = DarkColors.Primary,
    onPrimary = DarkColors.OnPrimary,
    primaryContainer = DarkColors.PrimaryVariant,
    onPrimaryContainer = DarkColors.OnPrimary,

    secondary = DarkColors.Secondary,
    onSecondary = DarkColors.OnSecondary,
    secondaryContainer = DarkColors.SecondaryVariant,
    onSecondaryContainer = DarkColors.OnSecondary,

    tertiary = DarkColors.TemplateColor1,
    onTertiary = DarkColors.OnPrimary,
    tertiaryContainer = DarkColors.TemplateColor1.copy(alpha = 0.2f),
    onTertiaryContainer = DarkColors.TemplateColor1,

    background = DarkColors.Background,
    onBackground = DarkColors.OnBackground,

    surface = DarkColors.Surface,
    onSurface = DarkColors.OnSurface,
    surfaceVariant = DarkColors.SurfaceVariant,
    onSurfaceVariant = DarkColors.OnSurfaceVariant,

    error = DarkColors.Error,
    onError = DarkColors.OnPrimary,
    errorContainer = DarkColors.ErrorContainer,
    onErrorContainer = DarkColors.Error,

    outline = DarkColors.Outline,
    outlineVariant = DarkColors.OutlineVariant,
    scrim = DarkColors.Scrim
)

/**
 * Theme mode configuration
 */
enum class ThemeMode {
    LIGHT,
    DARK,
    AUTO  // Uses system preference
}


/**
 * Main theme composable
 *
 * @param darkMode Force dark mode when true. When null, uses system preference.
 * @param themePreferences Optional ThemePreferences to listen to mood changes
 * @param content The composable content to apply the theme to.
 */
@Composable
fun DigitalDiaryTheme(
    darkMode: Boolean? = null,
    themePreferences: ThemePreferences? = null,
    content: @Composable () -> Unit
) {
    // Determine if dark mode should be used
    val useDarkMode = when {
        darkMode != null -> darkMode  // Force dark/light mode if specified
        else -> isSystemInDarkTheme()  // Use system preference
    }

    // Get the base color scheme
    var colorScheme = if (useDarkMode) {
        getDarkColorScheme()
    } else {
        getLightColorScheme()
    }

    // Override primary color if mood is selected
    themePreferences?.selectedMood?.let { mood ->
        val moodColor = mood.getColor()
        colorScheme = colorScheme.copy(
            primary = moodColor,
            primaryContainer = moodColor.copy(alpha = 0.2f)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = DiaryTypography,
        content = content
    )
}

/**
 * Extension to easily access template colors based on theme
 */
@Composable
fun getTemplateColors(): List<androidx.compose.ui.graphics.Color> {
    val isDarkMode = isSystemInDarkTheme()
    return if (isDarkMode) {
        listOf(
            DarkColors.TemplateColor1,
            DarkColors.TemplateColor2,
            DarkColors.TemplateColor3,
            DarkColors.TemplateColor4,
            DarkColors.TemplateColor5,
            DarkColors.TemplateColor6
        )
    } else {
        listOf(
            LightColors.TemplateColor1,
            LightColors.TemplateColor2,
            LightColors.TemplateColor3,
            LightColors.TemplateColor4,
            LightColors.TemplateColor5,
            LightColors.TemplateColor6
        )
    }
}

/**
 * Get a specific template color by index
 */
@Composable
fun getTemplateColor(index: Int): androidx.compose.ui.graphics.Color {
    val colors = getTemplateColors()
    return colors[index % colors.size]
}

/**
 * Template color names for reference
 */
object TemplateColorNames {
    const val COLOR_1_NAME = "Love & Romance"      // Pink
    const val COLOR_2_NAME = "Energy & Work"       // Amber
    const val COLOR_3_NAME = "Success & Growth"    // Emerald
    const val COLOR_4_NAME = "Dreams & Creativity" // Violet
    const val COLOR_5_NAME = "Passion & Adventure" // Rose
    const val COLOR_6_NAME = "Calm & Peace"        // Cyan
}
