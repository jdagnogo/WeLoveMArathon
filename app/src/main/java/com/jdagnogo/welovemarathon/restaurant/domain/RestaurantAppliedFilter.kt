package com.jdagnogo.welovemarathon.restaurant.domain

import androidx.annotation.Keep

@Keep
data class RestaurantAppliedFilter(
    val typeOfFilters: Set<String> = setOf(),
    val services: Set<String> = setOf(),
    val cuisines : Set<String> = setOf(),
    val plates : Set<String> = setOf(),
    val drinks : Set<String> = setOf(),
    val location : Set<String> = setOf(),
    val prices : Set<String> = setOf(),
) 