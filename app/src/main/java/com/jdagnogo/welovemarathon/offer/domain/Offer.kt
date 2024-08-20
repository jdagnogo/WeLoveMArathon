package com.jdagnogo.welovemarathon.offer.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.offer.data.OfferEntity
import com.jdagnogo.welovemarathon.offer.data.Promos
import java.sql.Timestamp
import java.util.Date

@Keep
data class Offer(
    val id: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val restaurant: String = "",
    val promos: List<Promo> = listOf(),
) {
    fun toOfferEntity(): OfferEntity {
        return OfferEntity(
            id = id,
            startDate = startDate,
            endDate = endDate,
            restaurant = restaurant,
            promos = Promos(promos),
        )
    }
}

@Keep
data class Promo(
    val icon: String = "",
    val title: String = "",
    val subTitle: String = "",
)