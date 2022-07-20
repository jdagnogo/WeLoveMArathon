package com.jdagnogo.welovemarathon.common.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.BuildConfig
import java.util.*

@Keep
enum class DataType(val timeType: Int, val value: Int) {
    ABOUT(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    BEACH(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    BLOG(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    FOOD(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    HOME(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    ACTIVITIES(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    BANNER(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    RUN(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    SHOPPING(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    SHOPPING_CATEGORIES(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    ACTIVITIES_CATEGORIES(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    FOOD_CATEGORIES(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    SHOPPING_TAG(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    TIPS(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    CULTURE(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    SPORT(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    WINE_TOUR(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    WINE_SOCIAL(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
    SPORT_CATEGORIES(Calendar.MINUTE, BuildConfig.EXPIRATION_IN_MINUTES),
}

fun DataType.generateNextUpdate(): Long {
    val cal: Calendar = Calendar.getInstance()
    cal.add(timeType, value)
    return cal.timeInMillis
}
