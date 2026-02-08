package com.piappstudio.digitaldiary.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Digital Diary Theme
 * 
 * Provides ADA-compliant theming with:
 * - Dynamic Light/Dark mode switching
 * - Mode-specific typography for better readability
 * - Accessible color schemes (WCAG 2.1 Level AA)
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
    tertiaryContainer = LightColors.TemplateColor1.copy(alpha = 0.1f),
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
    tertiaryContainer = DarkColors.TemplateColor1.copy(alpha = 0.1f),
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

enum class ThemeMode {
    LIGHT,
    DARK,
    AUTO
}

@Composable
fun DigitalDiaryTheme(
    darkMode: Boolean? = null,
    diaryMood: DiaryMood = DiaryMood.LOVE,
    themePreferences: ThemePreferences? = null,
    content: @Composable () -> Unit
) {
    // Determine the effective mode
    val useDarkMode = when {
        darkMode != null -> darkMode
        themePreferences != null -> themePreferences.isDarkModeEnabled()
        else -> isSystemInDarkTheme()
    }

    // Select the base color scheme
    var colorScheme = if (useDarkMode) getDarkColorScheme() else getLightColorScheme()

    // Apply mood override if selected, ensuring it uses the mode-specific template color
    themePreferences?.selectedMood?.let { mood ->
        val moodColor = if (useDarkMode) {
            getDarkTemplateColors()[mood.colorIndex % 6]
        } else {
            getLightTemplateColors()[mood.colorIndex % 6]
        }
        
        colorScheme = colorScheme.copy(
            primary = moodColor,
            primaryContainer = moodColor.copy(alpha = 0.15f),
            onPrimaryContainer = moodColor
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = getDiaryTypography(isDarkMode = useDarkMode, diaryMood),
        content = content
    )
}

/**
 * Helper functions to get mode-specific template colors
 */
fun getLightTemplateColors() = listOf(
    LightColors.TemplateColor1,
    LightColors.TemplateColor2,
    LightColors.TemplateColor3,
    LightColors.TemplateColor4,
    LightColors.TemplateColor5,
    LightColors.TemplateColor6
)

fun getDarkTemplateColors() = listOf(
    DarkColors.TemplateColor1,
    DarkColors.TemplateColor2,
    DarkColors.TemplateColor3,
    DarkColors.TemplateColor4,
    DarkColors.TemplateColor5,
    DarkColors.TemplateColor6
)

@Composable
fun getTemplateColors(): List<androidx.compose.ui.graphics.Color> {
    return if (isSystemInDarkTheme()) getDarkTemplateColors() else getLightTemplateColors()
}

@Composable
fun getTemplateColor(index: Int): androidx.compose.ui.graphics.Color {
    val colors = getTemplateColors()
    return colors[index % colors.size]
}

object TemplateColorNames {
    const val COLOR_1_NAME = "Love & Romance"
    const val COLOR_2_NAME = "Energy & Work"
    const val COLOR_3_NAME = "Success & Growth"
    const val COLOR_4_NAME = "Dreams & Creativity"
    const val COLOR_5_NAME = "Passion & Adventure"
    const val COLOR_6_NAME = "Calm & Peace"
}
