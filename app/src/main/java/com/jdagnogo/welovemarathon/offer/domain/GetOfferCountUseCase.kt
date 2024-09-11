package com.jdagnogo.welovemarathon.offer.domain

import com.jdagnogo.welovemarathon.offer.data.OfferRepository
import javax.inject.Inject

class GetOfferCountUseCase@Inject constructor(
    private val repository: OfferRepository,
) {
    operator fun invoke(id: String?): Int {
        return repository.offerActivated.value.data?.find {
            it.id == id
        }?.displayCount ?: 0
    }
}