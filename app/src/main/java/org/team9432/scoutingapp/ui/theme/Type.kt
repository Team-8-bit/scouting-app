package org.team9432.scoutingapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.team9432.scoutingapp.R

private val silkscreen = FontFamily(
    Font(R.font.silkscreen_regular, weight = FontWeight.Normal)
)

private val poppins = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = silkscreen,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = silkscreen,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
    ),
)