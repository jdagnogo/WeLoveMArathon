package com.jdagnogo.welovemarathon.offer.domain

import com.jdagnogo.welovemarathon.offer.data.OfferRepository
import javax.inject.Inject

class UpdateOfferDisplayCountUseCase @Inject constructor(
    private val repository: OfferRepository,
) {
    suspend operator fun invoke(id: String, count: Int = 1) {
        val currentOffer =
            repository.offerActivated.value.data?.find { it.id == id } ?: OfferActivated(
                id = id
            )

        val newCount = currentOffer.displayCount + count
        repository.updateOffer(currentOffer.copy(displayCount = newCount))
    }
}