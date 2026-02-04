package com.piappstudio.digitaldiary.common.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import digitaldiary.composeapp.generated.resources.*
import org.jetbrains.compose.resources.Font

@Composable
fun getDiaryTypography(isDarkMode: Boolean, diaryMood: DiaryMood = DiaryMood.LOVE): Typography {
    // Essential weights: Regular (Normal), Medium, SemiBold, Bold


    // Passion: Poppins
    val passion = FontFamily(
        Font(Res.font.Poppins_Regular, FontWeight.Normal),
        Font(Res.font.Poppins_Medium, FontWeight.Medium),
        Font(Res.font.Poppins_SemiBold, FontWeight.SemiBold),
        Font(Res.font.Poppins_Bold, FontWeight.Bold)
    )

    // Love: Playfair Display
    val love = FontFamily(
        Font(Res.font.Caveat_Regular, FontWeight.Normal),
        Font(Res.font.Caveat_Bold, FontWeight.Bold)
    )

    // Energy: Merienda
    val energy = FontFamily(
        Font(Res.font.Merienda_Regular, FontWeight.Normal),
        Font(Res.font.Merienda_Medium, FontWeight.Medium),
        Font(Res.font.Merienda_SemiBold, FontWeight.SemiBold),
        Font(Res.font.Merienda_Bold, FontWeight.Bold)
    )

    // Success: Lexend Giga
    val success = FontFamily(
        Font(Res.font.LexendGiga_Regular, FontWeight.Normal),
        Font(Res.font.LexendGiga_Medium, FontWeight.Medium),
        Font(Res.font.LexendGiga_SemiBold, FontWeight.SemiBold),
        Font(Res.font.LexendGiga_Bold, FontWeight.Bold)
    )

    // Creativity: Fira Sans
    val creativity = FontFamily(
        Font(Res.font.FiraSans_Regular, FontWeight.Normal),
        Font(Res.font.FiraSans_Medium, FontWeight.Medium),
        Font(Res.font.FiraSans_SemiBold, FontWeight.SemiBold),
        Font(Res.font.FiraSans_Bold, FontWeight.Bold)
    )

    // Calm: Montserrat
    val calm = FontFamily(
        Font(Res.font.Montserrat_Regular, FontWeight.Normal),
        Font(Res.font.Montserrat_Medium, FontWeight.Medium),
        Font(Res.font.Montserrat_SemiBold, FontWeight.SemiBold),
        Font(Res.font.Montserrat_Bold, FontWeight.Bold)
    )

    val fontFamily: FontFamily = when (diaryMood) {
        DiaryMood.LOVE -> love
        DiaryMood.ENERGY -> energy
        DiaryMood.SUCCESS -> success
        DiaryMood.CREATIVE -> creativity
        DiaryMood.PASSIONATE -> passion
        DiaryMood.CALM -> calm
    }

    val baseLetterSpacing = if (isDarkMode) 0.5.sp else 0.sp
    val bodyLineHeight = if (isDarkMode) 26.sp else 24.sp

    return Typography(
        displayLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = baseLetterSpacing
        ),
        displayMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp,
            lineHeight = 52.sp,
            letterSpacing = baseLetterSpacing
        ),
        displaySmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            letterSpacing = baseLetterSpacing
        ),
        headlineLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = baseLetterSpacing
        ),
        headlineMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = baseLetterSpacing
        ),
        headlineSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = baseLetterSpacing
        ),
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),
        titleSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = bodyLineHeight,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),
        bodySmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp
        ),
        labelLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
    )
}
