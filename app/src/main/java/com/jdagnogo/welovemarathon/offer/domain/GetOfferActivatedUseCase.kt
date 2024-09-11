package com.jdagnogo.welovemarathon.offer.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.offer.data.OfferRepository
import com.jdagnogo.welovemarathon.shopping.domain.transformContent
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class GetOfferActivatedUseCase @Inject constructor(
    private val repository: OfferRepository,
) {
    private val dateParser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    operator fun invoke(): Flow<Resource<List<OfferActivated>?>> {
        return repository.offerActivated.transformContent {
            it?.filter { offer ->
                filter(offer)
            }
        }
    }

    private fun filter(offer: OfferActivated): Boolean {
        val offerDate = parseDate(offer.activationDate) ?: return false
        val calendar = Calendar.getInstance()
        calendar.time = offerDate
        calendar.add(Calendar.HOUR, 3)
        return Date().time in offerDate.time..calendar.time.time
    }

    private fun parseDate(date: String?): Date? {
        if (date == null) return null
        return runCatching {
            dateParser.parse(date)
        }.getOrNull()
    }
}