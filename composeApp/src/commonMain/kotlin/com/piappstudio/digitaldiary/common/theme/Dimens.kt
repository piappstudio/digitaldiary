package com.piappstudio.digitaldiary.common.theme

import androidx.compose.ui.unit.dp

/**
 * Design tokens for spacing, sizing, and corner radius
 * Follows Material Design 3 spacing scale
 */
object Dimens {
    // Spacing scale (vertical rhythm)
    val space_0 = 0.dp
    val half_space = 4.dp          // xs
    val space = 8.dp               // sm
    val big_space = 16.dp          // md
    val bigger_space = 24.dp       // lg
    val biggest_space = 32.dp      // xl
    val space_40 = 40.dp           // 2xl
    val space_48 = 48.dp           // 3xl
    val space_56 = 56.dp           // 4xl
    val space_64 = 64.dp           // 5xl

    // Corner radius / Border radius
    val corner_xs = 4.dp           // Extra small radius
    val corner_sm = 8.dp           // Small radius
    val corner_md = 12.dp          // Medium radius
    val corner_lg = 16.dp          // Large radius
    val corner_xl = 20.dp          // Extra large radius
    val corner_2xl = 24.dp         // 2xl radius
    val corner_full = 999.dp       // Fully rounded (pill shape)

    // Component specific sizes
    val icon_size_xs = 16.dp       // Extra small icon
    val icon_size_sm = 20.dp       // Small icon
    val icon_size_md = 24.dp       // Medium icon (default)
    val icon_size_lg = 32.dp       // Large icon
    val icon_size_xl = 48.dp       // Extra large icon

    // Button sizes
    val button_height_sm = 32.dp   // Small button height
    val button_height_md = 40.dp   // Medium button height (default)
    val button_height_lg = 48.dp   // Large button height

    // Card/Surface padding
    val card_padding_sm = 12.dp
    val card_padding_md = 16.dp
    val card_padding_lg = 20.dp
    val card_padding_xl = 24.dp

    // Elevation / Shadows
    val elevation_0 = 0.dp
    val elevation_1 = 1.dp
    val elevation_2 = 2.dp
    val elevation_3 = 3.dp
    val elevation_4 = 4.dp
    val elevation_6 = 6.dp
    val elevation_8 = 8.dp
    val elevation_12 = 12.dp
    val elevation_16 = 16.dp
    val elevation_24 = 24.dp

    // Divider / Stroke sizes
    val stroke_thin = 0.5.dp
    val stroke_default = 1.dp
    val stroke_medium = 2.dp
    val stroke_thick = 4.dp

    // Breakpoint sizes (for responsive design)
    val breakpoint_mobile = 360.dp
    val breakpoint_tablet = 600.dp
    val breakpoint_desktop = 900.dp

    // Content maximum width (for centered content)
    val max_content_width = 800.dp

    // Diary entry specific dimensions
    val entry_card_min_height = 120.dp
    val entry_card_max_height = 400.dp
}