package com.piappstudio.digitaldiary.common.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

/**
 * Theme preview composable for design system showcase
 * Shows all 6 template colors, typography styles, and spacing tokens
 */

@Preview
@Composable
fun ThemePreview() {
    DigitalDiaryTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(Dimens.bigger_space)
        ) {
            // Title
            Text(
                "Digital Diary Theme System",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = Dimens.bigger_space)
            )

            // Template Colors Section
            Text(
                "6 Catching Template Colors",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = Dimens.big_space)
            )

            val templateColors = getTemplateColors()
            val templateNames = listOf(
                TemplateColorNames.COLOR_1_NAME,
                TemplateColorNames.COLOR_2_NAME,
                TemplateColorNames.COLOR_3_NAME,
                TemplateColorNames.COLOR_4_NAME,
                TemplateColorNames.COLOR_5_NAME,
                TemplateColorNames.COLOR_6_NAME
            )

            templateColors.forEachIndexed { index, color ->
                ColorPreviewCard(
                    color = color,
                    name = templateNames[index],
                    modifier = Modifier.padding(bottom = Dimens.big_space)
                )
            }

            // Typography Section
            Text(
                "Typography System",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = Dimens.bigger_space)
            )

            Text(
                "Display Large",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(bottom = Dimens.space)
            )
            Text(
                "Display Medium",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(bottom = Dimens.space)
            )
            Text(
                "Display Small",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = Dimens.space)
            )

            Text(
                "Headline Large",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = Dimens.space)
            )
            Text(
                "Headline Medium",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = Dimens.space)
            )
            Text(
                "Headline Small",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = Dimens.space)
            )

            Text(
                "Body Large - This is the main content text style used for diary entries and detailed descriptions. It provides excellent readability.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = Dimens.space)
            )
            Text(
                "Body Medium - Secondary content text",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = Dimens.space)
            )
            Text(
                "Body Small - Tertiary text",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = Dimens.bigger_space)
            )

            // Spacing Scale Section
            Text(
                "Spacing Scale",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = Dimens.bigger_space)
            )

            SpacingPreview(
                label = "half_space (4dp)",
                height = Dimens.half_space
            )
            SpacingPreview(
                label = "space (8dp)",
                height = Dimens.space
            )
            SpacingPreview(
                label = "big_space (16dp)",
                height = Dimens.big_space
            )
            SpacingPreview(
                label = "bigger_space (24dp)",
                height = Dimens.bigger_space
            )
            SpacingPreview(
                label = "biggest_space (32dp)",
                height = Dimens.biggest_space,
                modifier = Modifier.padding(bottom = Dimens.bigger_space)
            )

            // Corner Radius Section
            Text(
                "Corner Radius",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(vertical = Dimens.bigger_space)
            )

            val corners = listOf(
                "xs (4dp)" to Dimens.corner_xs,
                "sm (8dp)" to Dimens.corner_sm,
                "md (12dp)" to Dimens.corner_md,
                "lg (16dp)" to Dimens.corner_lg,
                "xl (20dp)" to Dimens.corner_xl
            )

            corners.forEach { (label, radius) ->
                CornerPreview(label = label, cornerRadius = radius)
            }
        }
    }
}

@Composable
private fun ColorPreviewCard(
    color: androidx.compose.ui.graphics.Color,
    name: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.biggest_space + Dimens.bigger_space)
                .background(color, shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimens.corner_md))
        )
        Text(
            name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = Dimens.space)
        )
    }
}

@Composable
private fun SpacingPreview(
    label: String,
    height: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(bottom = Dimens.space)) {
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimens.corner_sm)
                )
        )
    }
}

@Composable
private fun CornerPreview(
    label: String,
    cornerRadius: androidx.compose.ui.unit.Dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimens.big_space),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(cornerRadius)
                )
                .height(Dimens.biggest_space)
                .fillMaxWidth(0.3f)
        )
    }
}
