package com.jdagnogo.welovemarathon.beach.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class BeachData(
    val beachDao: BeachDao,
    val beachRemoteData: BeachRemoteData,
    val beachMapper: BeachMapper,
    val dataFreshnessUseCase: DataFreshnessUseCase,
)
