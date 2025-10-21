package com.example.rmsjims.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.rmsjims.R

val appMainText = FontFamily(
    Font(
        resId = R.font.font_sf_pro_display,
        weight = FontWeight.Bold
    )
)

val appFont = FontFamily(
    Font(
        resId = R.font.font_alliance_regular_two,
        weight = FontWeight.Normal
    )
)
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = appFont,
        fontWeight = FontWeight.Normal,
        color = titleColor,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = appMainText,
        fontWeight = FontWeight.Normal,
        color = onSurfaceVariant,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = appFont,
        fontWeight = FontWeight.Normal,
        color = categoryColor,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
)
