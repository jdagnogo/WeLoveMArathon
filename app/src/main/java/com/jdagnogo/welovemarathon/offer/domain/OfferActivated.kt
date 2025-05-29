package com.jdagnogo.welovemarathon.offer.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.offer.data.OfferActivatedEntity

@Keep
data class OfferActivated(
    val id: String = "",
    val activationDate: String? = null,
    val displayCount: Int = 0,
) {
    fun toOfferActivatedEntity(): OfferActivatedEntity = OfferActivatedEntity(
        id, activationDate, displayCount
    )
}