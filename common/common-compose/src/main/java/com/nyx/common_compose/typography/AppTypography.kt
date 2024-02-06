package com.nyx.common_compose.typography

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nyx.common_compose.R

object AppTypography {
    val largeTitle = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
        lineHeight = 26.sp
    )

    val title1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        lineHeight = 20.8.sp
    )

    val title2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 18.2.sp
    )

    val title3 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        lineHeight = 15.6.sp
    )

    val title4 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 18.2.sp
    )

    val text1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 15.6.sp
    )

    val caption1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 11.sp,
        lineHeight = 12.sp
    )

    val buttonText1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        lineHeight = 15.6.sp
    )

    val buttonText2 = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 18.2.sp
    )

    val elementText = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 9.sp,
        lineHeight = 9.9.sp
    )

    val priceText = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
        fontWeight = FontWeight.W500,
        fontSize = 24.sp,
        lineHeight = 31.2.sp
    )

    val placeholderText = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 19.1.sp
    )

    val linkText = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
        fontWeight = FontWeight.W400,
        fontSize = 10.sp,
        lineHeight = 11.sp
    )
}
