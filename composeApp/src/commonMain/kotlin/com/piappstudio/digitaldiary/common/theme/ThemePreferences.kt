package com.piappstudio.digitaldiary.common.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * Theme preference settings manager
 * Handles user preferences for theme mode and diary mood
 */
class ThemePreferences {
    var themeMode by mutableStateOf(ThemeMode.AUTO)

    var selectedMood by mutableStateOf<DiaryMood?>(null)
        private set


    /**
     * Set the selected mood/template color
     * @param mood The diary mood to apply as primary color
     */
    fun setMood(mood: DiaryMood?) {
        selectedMood = mood
    }

    /**
     * Get the effective dark mode value
     * @return true if dark mode should be used, false otherwise
     */
    @Composable
    fun isDarkModeEnabled(): Boolean {
        return when (themeMode) {
            ThemeMode.DARK -> true
            ThemeMode.LIGHT -> false
            ThemeMode.AUTO -> androidx.compose.foundation.isSystemInDarkTheme()
        }
    }

    /**
     * Toggle between dark and light mode
     */
    fun toggleTheme() {
        themeMode = when (themeMode) {
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.LIGHT -> ThemeMode.AUTO
            ThemeMode.AUTO -> ThemeMode.DARK
        }
    }

    /**
     * Reset to system preference
     */
    fun resetToSystem() {
        themeMode = ThemeMode.AUTO
    }
}

/**
 * Composable function to remember theme preferences
 */
@Composable
fun rememberThemePreferences(): ThemePreferences {
    return remember {
        ThemePreferences()
    }
}

/**
 * Extension function to get dark mode boolean from ThemeMode
 */
fun ThemeMode.isDarkMode(): Boolean? {
    return when (this) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.AUTO -> null  // Will use system preference
    }
}
