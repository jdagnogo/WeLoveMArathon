package com.jdagnogo.welovemarathon.offer.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.offer.data.OfferRepository
import com.jdagnogo.welovemarathon.restaurant.domain.GetRestaurantByIdUseCase
import com.jdagnogo.welovemarathon.shopping.domain.transformContent
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class GetOfferUseCase @Inject constructor(
    private val repository: OfferRepository,
    private val getRestaurantByIdUseCase: GetRestaurantByIdUseCase,
    private val isOfferAvailableUseCase: IsOfferAvailableUseCase,
) {
    operator fun invoke(): Flow<Resource<OfferWithRestaurant?>> {
        return repository.data.transformContent { data ->
            val restaurant = getRestaurantByIdUseCase(data?.firstOrNull()?.restaurant)
            val offer = data?.firstOrNull() ?: return@transformContent null

            if (isOfferAvailableUseCase(offer.id).not()) return@transformContent null
            OfferWithRestaurant(
                id = offer.id,
                title = offer.title,
                description = offer.description,
                startDate = toDate(offer.startDate),
                endDate = toDate(offer.endDate),
                restaurant = restaurant.data,
                promos = offer.promos,
                restaurantName = restaurant.data?.name ?: "",
                restaurantId = offer.restaurant
            )
        }
    }
}

private const val FORMAT_SLASH: String = "dd/MM/yyyy"
private fun toDate(date: String?): Date {
    return runCatching {
        if (date == null) return Date()
        val dateFormat = SimpleDateFormat(FORMAT_SLASH, Locale.FRANCE)
        return dateFormat.parse(date) as Date
    }.getOrElse { Date() }
}
