package com.piappstudio.digitaldiary.common.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Convenience composable to access current theme colors
 */
@Composable
fun getCurrentThemeColors(): ThemeColorSet {
    val isDarkMode = androidx.compose.foundation.isSystemInDarkTheme()

    return if (isDarkMode) {
        ThemeColorSet.darkColorSet()
    } else {
        ThemeColorSet.lightColorSet()
    }
}

/**
 * Color set wrapper for easier theme color access
 */
data class ThemeColorSet(
    // Primary colors
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val secondaryVariant: Color,

    // Template colors
    val templateColors: List<Color>,

    // Functional colors
    val error: Color,
    val success: Color,
    val warning: Color,

    // Background and surface
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,

    // Text colors
    val onBackground: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,

    // Semantic
    val outline: Color,
    val outlineVariant: Color
) {
    companion object {
        fun lightColorSet() = ThemeColorSet(
            primary = LightColors.Primary,
            primaryVariant = LightColors.PrimaryVariant,
            secondary = LightColors.Secondary,
            secondaryVariant = LightColors.SecondaryVariant,
            templateColors = listOf(
                LightColors.TemplateColor1,
                LightColors.TemplateColor2,
                LightColors.TemplateColor3,
                LightColors.TemplateColor4,
                LightColors.TemplateColor5,
                LightColors.TemplateColor6
            ),
            error = LightColors.Error,
            success = LightColors.Success,
            warning = LightColors.Warning,
            background = LightColors.Background,
            surface = LightColors.Surface,
            surfaceVariant = LightColors.SurfaceVariant,
            onBackground = LightColors.OnBackground,
            onSurface = LightColors.OnSurface,
            onSurfaceVariant = LightColors.OnSurfaceVariant,
            outline = LightColors.Outline,
            outlineVariant = LightColors.OutlineVariant
        )

        fun darkColorSet() = ThemeColorSet(
            primary = DarkColors.Primary,
            primaryVariant = DarkColors.PrimaryVariant,
            secondary = DarkColors.Secondary,
            secondaryVariant = DarkColors.SecondaryVariant,
            templateColors = listOf(
                DarkColors.TemplateColor1,
                DarkColors.TemplateColor2,
                DarkColors.TemplateColor3,
                DarkColors.TemplateColor4,
                DarkColors.TemplateColor5,
                DarkColors.TemplateColor6
            ),
            error = DarkColors.Error,
            success = DarkColors.Success,
            warning = DarkColors.Warning,
            background = DarkColors.Background,
            surface = DarkColors.Surface,
            surfaceVariant = DarkColors.SurfaceVariant,
            onBackground = DarkColors.OnBackground,
            onSurface = DarkColors.OnSurface,
            onSurfaceVariant = DarkColors.OnSurfaceVariant,
            outline = DarkColors.Outline,
            outlineVariant = DarkColors.OutlineVariant
        )
    }
}

/**
 * Utility extension to get a template color by mood/category
 */
@Suppress("unused")
enum class DiaryMood(val colorIndex: Int, val displayName: String) {
    LOVE(0, TemplateColorNames.COLOR_1_NAME),
    ENERGY(1, TemplateColorNames.COLOR_2_NAME),
    SUCCESS(2, TemplateColorNames.COLOR_3_NAME),
    CREATIVE(3, TemplateColorNames.COLOR_4_NAME),
    PASSIONATE(4, TemplateColorNames.COLOR_5_NAME),
    CALM(5, TemplateColorNames.COLOR_6_NAME);

    @Composable
    fun getColor(): Color {
        return getTemplateColor(colorIndex)
    }
}
