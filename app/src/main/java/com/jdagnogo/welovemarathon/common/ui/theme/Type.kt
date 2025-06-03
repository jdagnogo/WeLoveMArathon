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
    Font(R.font.arial_bold, weight = FontWeight.Bold),
    Font(R.font.arial, weight = FontWeight.Light),
    Font(R.font.arial, weight = FontWeight.Thin),
    Font(R.font.arial, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.arial)
)

val normal_font = FontFamily(
    Font(R.font.arial_bold, weight = FontWeight.Bold),
    Font(R.font.arial, weight = FontWeight.Light),
    Font(R.font.arial, weight = FontWeight.Thin),
    Font(R.font.arial, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.arial)
)

val WLMTitleFont = FontFamily(
    Font(R.font.wlm_title_font, FontWeight.Normal)
)

val WLMTitleStyle = TextStyle(
    fontFamily = WLMTitleFont,
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp,
    color = Color(0xFF1E4F7B)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        color = Color.Black
    ),
    subtitle1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        letterSpacing = 0.15.sp,
        color = Color.Black
    ),
    button = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    ),
    h1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        letterSpacing = 0.sp,
        color = Color.Black
    ),
    h2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
        color = Color.DarkGray
    ),
    h3 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
        color = Color.DarkGray
    ),
    h4 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.sp,
        color = Color.Gray
    ),
    h5 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        letterSpacing = 0.sp,
        color = Color.LightGray
    ),
    h6 = TextStyle(
        fontFamily = WLMTitleFont,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        letterSpacing = 0.sp,
        color = Color.Black
    )
)

val TitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    color = Color.White ,
    textAlign = TextAlign.Center,
    fontSize = 20.sp
)

val SubTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Normal,
    color = Color.White ,
    textAlign = TextAlign.Center,
    fontSize = 16.sp
)

val OfferTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    textAlign = TextAlign.Center,
    fontSize = 32.sp
)

val ActivityTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    textAlign = TextAlign.Center,
    fontSize = 14.sp
)

val ActivitySubTitleStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    color = Color(0xFF1E4F7B),
    textAlign = TextAlign.Center,
    fontSize = 12.sp
)

val CategoryGridTagStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Normal,
    color = Color(0xFF1E4F7B),
    fontSize = 12.sp
)

val BeachTitleStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    color = Color(0xFF1E4F7B),
    textAlign = TextAlign.Center,
    fontSize = 16.sp
)

val SplashScreenTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Normal,
    color = Color(0xFF1E4F7B),
    textAlign = TextAlign.Center,
    fontSize = 18.sp
)

val RecommendedCategoryTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Bold,
    color = Color(0xFF1E4F7B),
    fontSize = 14.sp
)

val tagsTitleStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 10.sp
)

val recommendedCategoryContentStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Normal,
    color = Color.Black,
    fontSize = 14.sp
)

val tagStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    color = Color.Black,
    fontSize = 14.sp
)

val filterButtonStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    color = Color(0xFF1E4F7B),
    fontSize = 14.sp
)

val contactStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Normal,
    color = Color(0xFF1E4F7B),
    fontSize = 16.sp
)

val emptyScreenTitle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Bold,
    color = Color(0xFF1E4F7B),
    fontSize = 18.sp
)

val wineDescription = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Normal,
    color = Color.Black,
    fontSize = 16.sp
)

val descriptionStyle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Normal,
    color = Color(0xFF333333),
    fontSize = 14.sp
)

val emptyScreenSubTitle = TextStyle(
    fontFamily = normal_font,
    fontWeight = FontWeight.Normal,
    color = Color.Gray,
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
    color = Color(0xFF1E4F7B),
    textAlign = TextAlign.Center,
    fontSize = 16.sp
)

val BottomNavTitleStyle = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    color = Color(0xFF1E4F7B)
)