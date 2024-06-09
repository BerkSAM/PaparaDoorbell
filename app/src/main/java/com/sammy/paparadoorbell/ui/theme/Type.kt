package com.sammy.paparadoorbell.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sammy.paparadoorbell.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val mediumfont : FontFamily = FontFamily(
       Font(R.font.poppins_medium, FontWeight.Medium)
)

val boldfont : FontFamily = FontFamily(
    Font(R.font.poppins_bold, FontWeight.Medium)
)
val regularfont : FontFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Medium)
)
val semiboldfont : FontFamily = FontFamily(
    Font(R.font.poppins_semibold, FontWeight.Medium)
)

val thin : FontFamily = FontFamily(
    Font(R.font.poppins_thin, FontWeight.Medium)
)

val black : FontFamily = FontFamily(
    Font(R.font.poppins_black, FontWeight.Medium)
)

val ubuntusans : FontFamily = FontFamily(
    Font(R.font.ubuntusans_regular, FontWeight.Medium)
)

val meriandamedium : FontFamily = FontFamily(
    Font(R.font.merienda_medium, FontWeight.Medium)
)