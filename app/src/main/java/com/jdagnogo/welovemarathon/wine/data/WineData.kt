package com.jdagnogo.welovemarathon.wine.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class WineData(
    val dao: WineDao,
    val remoteData: WineRemoteData,
    val dataFreshnessUseCase: DataFreshnessUseCase,
    val mapper: WineMapper
)