package com.jdagnogo.welovemarathon.offer.domain

import com.jdagnogo.welovemarathon.offer.data.OfferRepository
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class ActivateOfferUseCase @Inject constructor(
    private val repository: OfferRepository,
) {
    private val dateParser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    suspend operator fun invoke(id: String, date: Date) {
        val currentOffer = repository.offerActivated.value.data?.find { it.id == id } ?: return
        val dateFormatted = dateParser.format(date)
        repository.updateOffer(currentOffer.copy(activationDate = dateFormatted))
    }
}