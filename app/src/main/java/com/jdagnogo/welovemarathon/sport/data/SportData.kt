package com.jdagnogo.welovemarathon.sport.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class SportData(
    val dao: SportDao,
    val mapper: SportMapper,
    val remoteData: SportRemoteData,
    val dataFreshnessUseCase: DataFreshnessUseCase,
)
