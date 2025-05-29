package com.jdagnogo.welovemarathon.offer.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantDao

@Keep
data class OfferData(
    val dao: OfferDao,
    val remoteData: OfferRemoteData,
    val dataFreshnessUseCase: DataFreshnessUseCase,
    val mapper: OfferMapper
)
