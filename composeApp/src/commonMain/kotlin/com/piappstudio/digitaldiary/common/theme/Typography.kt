package com.piappstudio.digitaldiary.common.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import digitaldiary.composeapp.generated.resources.Caveat_Bold
import digitaldiary.composeapp.generated.resources.Caveat_Regular
import digitaldiary.composeapp.generated.resources.Inter_Medium
import digitaldiary.composeapp.generated.resources.Inter_Regular
import digitaldiary.composeapp.generated.resources.Inter_SemiBold
import digitaldiary.composeapp.generated.resources.Poppins_Bold
import digitaldiary.composeapp.generated.resources.Poppins_Light
import digitaldiary.composeapp.generated.resources.Poppins_Medium
import digitaldiary.composeapp.generated.resources.Poppins_Regular
import digitaldiary.composeapp.generated.resources.Poppins_SemiBold
import digitaldiary.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font


@Suppress("unused")
@Composable
fun getDiaryTypography():Typography {

    // Define custom font families
    val poppinsFont = FontFamily(
        Font(Res.font.Poppins_Light, FontWeight.Light),
        Font(Res.font.Poppins_Regular, FontWeight.Normal),
        Font(Res.font.Poppins_Medium, FontWeight.Medium),
        Font(Res.font.Poppins_SemiBold, FontWeight.SemiBold),
        Font(Res.font.Poppins_Bold, weight = FontWeight.Bold)
    )

    val caveatFont = FontFamily(
        Font(Res.font.Caveat_Regular, FontWeight.Normal
        ), Font(Res.font.Caveat_Bold, FontWeight.Bold)
    )



    val interFont = FontFamily(
        Font(Res.font.Inter_Regular, FontWeight.Normal),
        Font(Res.font.Inter_Medium, FontWeight.Medium),
        Font(Res.font.Inter_SemiBold, FontWeight.SemiBold)
    )
    return Typography(


        // Display styles - largest, for headlines
        displayLarge = TextStyle(
            fontFamily = poppinsFont,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp
        ),
        displayMedium = TextStyle(
            fontFamily = poppinsFont,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.sp
        ),
        displaySmall = TextStyle(
            fontFamily = poppinsFont,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp
        ),

        // Headline styles - for section headers
        headlineLarge = TextStyle(
            fontFamily = poppinsFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = poppinsFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = poppinsFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.sp
        ),

        // Title styles - for cards and dialogs
        titleLarge = TextStyle(
            fontFamily = poppinsFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        titleMedium = TextStyle(
            fontFamily = poppinsFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        titleSmall = TextStyle(
            fontFamily = poppinsFont,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),

        // Body styles - for main content
        bodyLarge = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp
        ),
        bodySmall = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp
        ),

        // Label styles - for buttons, tags, etc.
        labelLarge = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        labelMedium = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.5.sp
        )

    )
}

/**
 * Custom typography variants for specific use cases
 */
/*@Suppress("unused")
object CustomTypography {
    // For diary entry titles - using Caveat for handwritten feel
    @Suppress("unused")
    val diaryEntryTitle = TextStyle(
        fontFamily = caveatFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        fontStyle = FontStyle.Italic
    )

    // For diary entry body text - using Inter for readability
    @Suppress("unused")
    val diaryEntryBody = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.2.sp
    )

    // For mood/emotion indicators - using Poppins for clarity
    @Suppress("unused")
    val moodIndicator = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    )

    // For timestamps - using Inter for consistency
    @Suppress("unused")
    val timestamp = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.3.sp
    )

    // For category tags - using Poppins for emphasis
    @Suppress("unused")
    val categoryTag = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    )
}*/
