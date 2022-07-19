package com.jdagnogo.welovemarathon.common.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jdagnogo.welovemarathon.R

val fonts = FontFamily(
    Font(R.font.wlm_font, weight = FontWeight.Bold),
    Font(R.font.wlm_font, weight = FontWeight.Light),
    Font(R.font.wlm_font, weight = FontWeight.Thin),
    Font(R.font.wlm_font, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.wlm_font)
)

val normal_font = FontFamily(
    Font(R.font.arial_bold, weight = FontWeight.Bold),
    Font(R.font.arial, weight = FontWeight.Light),
    Font(R.font.arial, weight = FontWeight.Thin),
    Font(R.font.arial, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.arial)
)

// Set of Material typography styles to start with
val Typography = Typography(

    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
        color = Black
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val BottomNavTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp
)

val TitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Normal,
    color = White,
    textAlign = TextAlign.Center,
    fontSize = 20.sp
)


val SubTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Normal,
    color = White,
    textAlign = TextAlign.Center,
    fontSize = 14.sp
)

val ActivityTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Normal,
    color = White,
    textAlign = TextAlign.Center,
    fontSize = 14.sp
)

val ActivitySubTitleStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    color = White,
    textAlign = TextAlign.Center,
    fontSize = 12.sp
)

val CategoryGridTagStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Normal,
    color = Primary,
    fontSize = 12.sp
)

val BeachTitleStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    color = White,
    textAlign = TextAlign.Center,
    fontSize = 16.sp
)

val SplashScreenTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Normal,
    color = White,
    textAlign = TextAlign.Center,
    fontSize = 18.sp
)

val RecommendedCategoryTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Bold,
    color = White,
    fontSize = 14.sp
)

val tagsTitleStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    color = TagColor,
    fontSize = 12.sp
)

val recommendedCategoryContentStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp
)

val tagStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp
)

val filterButtonStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    color = White,
    fontSize = 14.sp
)

val contactStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Normal,
    color = White,
    fontSize = 16.sp
)

val emptyScreenTitle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    color = White,
    fontSize = 18.sp
)

val wineDescription = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Normal,
    color = White,
    fontSize = 16.sp
)

val emptyScreenSubTitle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Normal,
    color = White,
    textAlign = TextAlign.Center,
    fontSize = 14.sp
)

val clearAllStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    color = Color.Red,
    textAlign = TextAlign.Center,
    fontSize = 14.sp
)

val RecommendedCategoryItemTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Normal,
    color = White,
    textAlign = TextAlign.Center,
    fontSize = 16.sp
)
