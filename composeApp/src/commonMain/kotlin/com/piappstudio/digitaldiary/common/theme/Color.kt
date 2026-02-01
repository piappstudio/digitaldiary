package com.piappstudio.digitaldiary.common.theme

import androidx.compose.ui.graphics.Color

/**
 * Color palette for Digital Diary App
 * Contains 6 catching template colors plus semantic colors
 */

// Light Theme Colors
object LightColors {
    // Primary Colors - Main Brand Colors
    val Primary = Color(0xFF6366F1)           // Indigo
    val PrimaryVariant = Color(0xFF4F46E5)   // Darker Indigo
    val Secondary = Color(0xFF0EA5E9)        // Sky Blue
    val SecondaryVariant = Color(0xFF0284C7) // Darker Sky Blue

    // 6 Catching Template Colors for Diary Categories/Moods
    val TemplateColor1 = Color(0xFFEC4899)   // Pink - Love/Romance
    val TemplateColor2 = Color(0xFFF59E0B)   // Amber - Energy/Work
    val TemplateColor3 = Color(0xFF10B981)   // Emerald - Success/Growth
    val TemplateColor4 = Color(0xFF8B5CF6)   // Violet - Dreams/Creativity
    val TemplateColor5 = Color(0xFFF43F5E)   // Rose - Passion/Adventure
    val TemplateColor6 = Color(0xFF06B6D4)   // Cyan - Calm/Peace

    // Background Colors
    val Background = Color(0xFFFAFAFA)       // Off-white
    val Surface = Color(0xFFFFFFFF)          // Pure White
    val SurfaceVariant = Color(0xFFF3F4F6)   // Light Gray

    // Text Colors
    val OnPrimary = Color(0xFFFFFFFF)        // White
    val OnSecondary = Color(0xFFFFFFFF)      // White
    val OnBackground = Color(0xFF1F2937)    // Dark Gray
    val OnSurface = Color(0xFF111827)       // Almost Black
    val OnSurfaceVariant = Color(0xFF6B7280) // Medium Gray

    // Functional Colors
    val Error = Color(0xFFDC2626)                      // Red
    @Suppress("unused") val ErrorContainer = Color(0xFFFEE2E2)   // Light Red
    val Success = Color(0xFF10B981)                    // Green
    @Suppress("unused") val SuccessContainer = Color(0xFFD1FAE5) // Light Green
    val Warning = Color(0xFFF59E0B)                    // Amber
    @Suppress("unused") val WarningContainer = Color(0xFFFEF3C7) // Light Amber

    // Additional Semantic Colors
    val Outline = Color(0xFFD1D5DB)          // Light Border
    val OutlineVariant = Color(0xFFE5E7EB)   // Lighter Border
    val Scrim = Color(0xFF000000)            // Black (for scrim/overlay)
}

// Dark Theme Colors
object DarkColors {
    // Primary Colors
    val Primary = Color(0xFF818CF8)           // Light Indigo
    val PrimaryVariant = Color(0xFFA5B4FC)   // Lighter Indigo
    val Secondary = Color(0xFF38BDF8)        // Light Sky Blue
    val SecondaryVariant = Color(0xFF7DD3FC) // Lighter Sky Blue

    // 6 Catching Template Colors (adjusted for dark mode)
    val TemplateColor1 = Color(0xFFF472B6)   // Light Pink
    val TemplateColor2 = Color(0xFFFBBF24)   // Light Amber
    val TemplateColor3 = Color(0xFF6EE7B7)   // Light Emerald
    val TemplateColor4 = Color(0xFFD8B4FE)   // Light Violet
    val TemplateColor5 = Color(0xFFFB7185)   // Light Rose
    val TemplateColor6 = Color(0xFF22D3EE)   // Light Cyan

    // Background Colors
    val Background = Color(0xFF0F172A)       // Very Dark Blue
    val Surface = Color(0xFF1E293B)          // Dark Slate
    val SurfaceVariant = Color(0xFF334155)   // Medium Dark Slate

    // Text Colors
    val OnPrimary = Color(0xFF1F2937)        // Dark Gray (for text on primary)
    val OnSecondary = Color(0xFF1F2937)      // Dark Gray (for text on secondary)
    val OnBackground = Color(0xFFF1F5F9)    // Light Gray
    val OnSurface = Color(0xFFE2E8F0)       // Very Light Gray
    val OnSurfaceVariant = Color(0xFFCBD5E1) // Light Gray

    // Functional Colors
    val Error = Color(0xFFFCA5A5)                      // Light Red
    @Suppress("unused") val ErrorContainer = Color(0xFF7F1D1D)   // Dark Red
    val Success = Color(0xFF6EE7B7)                    // Light Green
    @Suppress("unused") val SuccessContainer = Color(0xFF064E3B) // Dark Green
    val Warning = Color(0xFFFBBF24)                    // Light Amber
    @Suppress("unused") val WarningContainer = Color(0xFF78350F) // Dark Amber

    // Additional Semantic Colors
    val Outline = Color(0xFF64748B)          // Medium Gray
    val OutlineVariant = Color(0xFF475569)   // Darker Gray
    val Scrim = Color(0xFF000000)            // Black (for scrim/overlay)
}
