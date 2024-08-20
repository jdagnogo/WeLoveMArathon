package com.jdagnogo.welovemarathon.offer.data

import com.jdagnogo.welovemarathon.offer.domain.Offer
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantEntity
import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant
import javax.inject.Inject

class OfferMapper @Inject constructor() {
    fun toDomain(offersEntities: List<OfferEntity>): List<Offer> {
        return offersEntities.map {
            it.toOffer()
        }
    }

    fun toEntities(offers: List<Offer>): List<OfferEntity> {
        return offers.map {
            it.toOfferEntity()
        }
    }

}
