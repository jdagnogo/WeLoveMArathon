package com.jdagnogo.welovemarathon.offer.domain

import com.jdagnogo.welovemarathon.offer.data.OfferRepository
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class IsOfferAvailableUseCase @Inject constructor(
    private val repository: OfferRepository,
) {
    private val dateParser = SimpleDateFormat("dd/MM/yyyy")
    operator fun invoke(id: String): Boolean {
        val offer = repository.data.value.data?.find {
            it.id == id
        } ?: return false
        val beginDate = parseDate(offer.startDate) ?: return false
        val endDate = parseDate(offer.endDate) ?: return false
        return Date().time in beginDate.time..endDate.time
    }

    private fun parseDate(date: String?): Date? {
        if (date == null) return null
        return runCatching {
            dateParser.parse(date)
        }.getOrNull()
    }
}