package com.piappstudio.digitaldiary.common.theme

import androidx.compose.ui.graphics.Color

/**
 * Color palette for Digital Diary App
 * Adjusted for ADA compliance (WCAG 2.1 Level AA)
 * Targets contrast ratio of at least 4.5:1 for normal text
 */

// Light Theme Colors
object LightColors {
    // Primary Colors
    val Primary = Color(0xFF4F46E5)           // Darker Indigo (Contrast ~6.5:1)
    val PrimaryVariant = Color(0xFF3730A3)   // Even Darker Indigo
    val Secondary = Color(0xFF0369A1)        // Darker Sky Blue (Contrast ~7:1)
    val SecondaryVariant = Color(0xFF075985)

    // ADA Compliant Template Colors for Light Mode (Contrast > 4.5:1 on white)
    val TemplateColor1 = Color(0xFFBE185D)   // Dark Pink
    val TemplateColor2 = Color(0xFFB45309)   // Dark Amber
    val TemplateColor3 = Color(0xFF047857)   // Dark Emerald
    val TemplateColor4 = Color(0xFF6D28D9)   // Dark Violet
    val TemplateColor5 = Color(0xFFBE123C)   // Dark Rose
    val TemplateColor6 = Color(0xFF0E7490)   // Dark Cyan

    // Background Colors
    val Background = Color(0xFFFAFAFA)
    val Surface = Color(0xFFFFFFFF)
    val SurfaceVariant = Color(0xFFF3F4F6)

    // Text Colors
    val OnPrimary = Color(0xFFFFFFFF)
    val OnSecondary = Color(0xFFFFFFFF)
    val OnBackground = Color(0xFF1F2937)
    val OnSurface = Color(0xFF111827)
    val OnSurfaceVariant = Color(0xFF4B5563) // Adjusted for better contrast

    // Functional Colors
    val Error = Color(0xFFB91C1C)            // Darker Red
    val ErrorContainer = Color(0xFFFEE2E2)
    val Success = Color(0xFF047857)
    val SuccessContainer = Color(0xFFD1FAE5)
    val Warning = Color(0xFFB45309)
    val WarningContainer = Color(0xFFFEF3C7)

    val Outline = Color(0xFF9CA3AF)          // Adjusted for better visibility
    val OutlineVariant = Color(0xFFE5E7EB)
    val Scrim = Color(0xFF000000)
}

// Dark Theme Colors
object DarkColors {
    // Primary Colors
    val Primary = Color(0xFF818CF8)           // Light Indigo (Contrast ~5.6:1)
    val PrimaryVariant = Color(0xFFA5B4FC)
    val Secondary = Color(0xFF38BDF8)        // Light Sky Blue (Contrast ~8.6:1)
    val SecondaryVariant = Color(0xFF7DD3FC)

    // ADA Compliant Template Colors for Dark Mode (Contrast > 4.5:1 on dark background)
    val TemplateColor1 = Color(0xFFF472B6)   // Light Pink
    val TemplateColor2 = Color(0xFFFBBF24)   // Light Amber
    val TemplateColor3 = Color(0xFF6EE7B7)   // Light Emerald
    val TemplateColor4 = Color(0xFFD8B4FE)   // Light Violet
    val TemplateColor5 = Color(0xFFFB7185)   // Light Rose
    val TemplateColor6 = Color(0xFF22D3EE)   // Light Cyan

    // Background Colors
    val Background = Color(0xFF0F172A)       // Very Dark Blue
    val Surface = Color(0xFF1E293B)
    val SurfaceVariant = Color(0xFF334155)

    // Text Colors
    val OnPrimary = Color(0xFF0F172A)
    val OnSecondary = Color(0xFF0F172A)
    val OnBackground = Color(0xFFF1F5F9)
    val OnSurface = Color(0xFFE2E8F0)
    val OnSurfaceVariant = Color(0xFFCBD5E1)

    // Functional Colors
    val Error = Color(0xFFFCA5A5)
    val ErrorContainer = Color(0xFF7F1D1D)
    val Success = Color(0xFF6EE7B7)
    val SuccessContainer = Color(0xFF064E3B)
    val Warning = Color(0xFFFBBF24)
    val WarningContainer = Color(0xFF78350F)

    val Outline = Color(0xFF64748B)
    val OutlineVariant = Color(0xFF475569)
    val Scrim = Color(0xFF000000)
}
