package com.jdagnogo.welovemarathon.restaurant.domain

import androidx.annotation.Keep

@Keep
data class RestaurantFilter(
    val typeOfFilters: List<IconNameFilter> = listOf(),
    val services: List<IconNameFilter> = listOf(),
    val cuisines : List<String> = listOf(),
    val plates : List<String> = listOf(),
    val drinks : List<String> = listOf(),
    val location : List<String> = listOf(),
    val prices : List<String> = listOf(),
) {
}
@Keep
data class IconNameFilter(
    val name : String,
    val icon : String,
)