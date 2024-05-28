package com.jdagnogo.welovemarathon.restaurant.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class RestaurantData(
    val dao: RestaurantDao,
    val remoteData: RestaurantRemoteData,
    val dataFreshnessUseCase: DataFreshnessUseCase,
    val mapper: RestaurantMapper
)