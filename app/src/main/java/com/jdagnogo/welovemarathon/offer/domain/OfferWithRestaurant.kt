package com.jdagnogo.welovemarathon.offer.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant
import java.util.Date

@Keep
data class OfferWithRestaurant(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val startDate: Date = Date(),
    val endDate: Date = Date(),
    val restaurant: Restaurant? = null,
    val promos: List<Promo> = listOf(),
)