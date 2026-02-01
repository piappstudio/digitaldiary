package com.piappstudio.digitaldiary.common.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

/**
 * Theme Testing Guide and Interactive Demo
 * Use this to verify theme implementation and test dark mode
 */

@Preview
@Composable
fun ThemeTestingGuide() {
    val (themeMode, setThemeMode) = remember { mutableStateOf(ThemeMode.AUTO) }

    DigitalDiaryTheme(
        darkMode = themeMode.isDarkMode()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(Dimens.bigger_space)
        ) {
            // Header
            Text(
                "Theme System Testing Guide",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = Dimens.bigger_space)
            )

            // Theme Mode Selector
            Text(
                "Theme Mode Control",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = Dimens.big_space)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimens.bigger_space),
                horizontalArrangement = Arrangement.spacedBy(Dimens.half_space)
            ) {
                ThemeMode.values().forEach { mode ->
                    Button(
                        onClick = { setThemeMode(mode) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(Dimens.half_space)
                    ) {
                        Text(mode.name, fontSize = MaterialTheme.typography.labelSmall.fontSize)
                    }
                }
            }

            Text(
                "Current Mode: ${themeMode.name}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = Dimens.bigger_space)
            )

            // Template Colors Test
            Text(
                "Template Colors Test",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = Dimens.big_space)
            )

            val templateNames = listOf(
                "Pink - Love",
                "Amber - Energy",
                "Emerald - Success",
                "Violet - Creative",
                "Rose - Passion",
                "Cyan - Calm"
            )

            getTemplateColors().forEachIndexed { index, color ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimens.big_space)
                        .background(
                            color,
                            shape = RoundedCornerShape(Dimens.corner_md)
                        )
                        .padding(Dimens.card_padding_md),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        templateNames[index],
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(Dimens.corner_sm)
                            )
                            .padding(Dimens.space)
                    ) {
                        Text(
                            "#${color.value.toString(16).uppercase().takeLast(6)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            // Mood Test
            Text(
                "Mood Colors Test",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = Dimens.big_space)
            )

            DiaryMood.values().forEach { mood ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            mood.getColor(),
                            shape = RoundedCornerShape(Dimens.corner_md)
                        )
                        .padding(Dimens.card_padding_md)
                        .padding(bottom = Dimens.big_space)
                ) {
                    Text(
                        mood.displayName,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Functional Colors Test
            Text(
                "Functional Colors Test",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = Dimens.big_space)
            )

            FunctionalColorTest(
                label = "Success",
                color = MaterialTheme.colorScheme.primary
            )
            FunctionalColorTest(
                label = "Error",
                color = MaterialTheme.colorScheme.error
            )
            FunctionalColorTest(
                label = "Background",
                color = MaterialTheme.colorScheme.background,
                isDark = true
            )
            FunctionalColorTest(
                label = "Surface",
                color = MaterialTheme.colorScheme.surface,
                isDark = true
            )

            // Dark Mode Indicator
            Text(
                "Dark Mode Status",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = Dimens.big_space)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(Dimens.corner_md)
                    )
                    .padding(Dimens.card_padding_md)
            ) {
                Text(
                    "System Dark Mode: ${isSystemInDarkTheme()}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            // Typography Test
            Text(
                "Typography Test",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = Dimens.big_space)
            )

            Text(
                "Display Large (32sp Bold)",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(bottom = Dimens.space)
            )
            Text(
                "Headline Large (22sp SemiBold)",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = Dimens.space)
            )
            Text(
                "Body Large (16sp Normal) - Use this for main diary entry content",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = Dimens.space)
            )
            Text(
                "Body Small (12sp Normal)",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = Dimens.bigger_space)
            )

            // Spacing Scale Test
            Text(
                "Spacing Scale Visual Test",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = Dimens.big_space)
            )

            listOf(
                "4dp" to Dimens.half_space,
                "8dp" to Dimens.space,
                "16dp" to Dimens.big_space,
                "24dp" to Dimens.bigger_space,
                "32dp" to Dimens.biggest_space
            ).forEach { (label, size) ->
                Text(label, style = MaterialTheme.typography.labelSmall)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(Dimens.corner_sm)
                        )
                        .padding(vertical = size)
                )
                Box(modifier = Modifier.padding(bottom = Dimens.big_space))
            }
        }
    }
}

@Composable
private fun FunctionalColorTest(
    label: String,
    color: androidx.compose.ui.graphics.Color,
    isDark: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color, shape = RoundedCornerShape(Dimens.corner_md))
            .padding(Dimens.card_padding_md)
            .padding(bottom = Dimens.big_space)
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyLarge,
            color = if (isDark) MaterialTheme.colorScheme.onBackground
            else MaterialTheme.colorScheme.onPrimary
        )
    }
}

/**
 * Testing Checklist
 *
 * Use this guide to verify all theme functionality:
 *
 * ✓ Colors
 *   - [ ] Light theme colors display correctly
 *   - [ ] Dark theme colors display correctly
 *   - [ ] 6 template colors visible and distinct
 *   - [ ] Functional colors (error, success) are visible
 *
 * ✓ Typography
 *   - [ ] Display styles are appropriately large
 *   - [ ] Headline styles are prominent
 *   - [ ] Body text is readable
 *   - [ ] Custom styles (diary entry) are correct
 *
 * ✓ Spacing
 *   - [ ] All spacing values are visually correct
 *   - [ ] Spacing creates proper rhythm
 *   - [ ] Padding is consistent
 *
 * ✓ Dark Mode
 *   - [ ] Dark mode theme applies correctly
 *   - [ ] Light mode theme applies correctly
 *   - [ ] Auto mode respects system preference
 *   - [ ] Color contrast is sufficient
 *
 * ✓ Accessibility
 *   - [ ] Text contrast passes WCAG AA
 *   - [ ] Interactive elements are clearly visible
 *   - [ ] Focus states are visible
 *
 * ✓ Multi-platform
 *   - [ ] Theme works on Android
 *   - [ ] Theme works on iOS
 *   - [ ] Theme works on Web
 *   - [ ] Theme works on Desktop
 */
