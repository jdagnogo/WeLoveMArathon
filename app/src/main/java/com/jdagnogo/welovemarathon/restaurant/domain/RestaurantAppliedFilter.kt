package com.jdagnogo.welovemarathon.restaurant.domain

import androidx.annotation.Keep

@Keep
data class RestaurantAppliedFilter(
    val typeOfFilters: MutableSet<String> = mutableSetOf(),
    val services: MutableSet<String> = mutableSetOf(),
    val cuisines : MutableSet<String> = mutableSetOf(),
    val plates : MutableSet<String> = mutableSetOf(),
    val drinks : MutableSet<String> = mutableSetOf(),
    val location : MutableSet<String> = mutableSetOf(),
    val prices : MutableSet<String> = mutableSetOf(),
    val handicapAccess : Boolean = false,
    val evCharger : Boolean = false,
)