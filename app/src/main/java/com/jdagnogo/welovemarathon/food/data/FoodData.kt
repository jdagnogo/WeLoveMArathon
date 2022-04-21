package com.jdagnogo.welovemarathon.food.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class FoodData(
    val dao: FoodDao,
    val remoteData: FoodRemoteData,
    val dataFreshnessUseCase: DataFreshnessUseCase,
    val mapper: FoodMapper
)
