package com.jdagnogo.welovemarathon.run.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class RunData(
    val runDao: RunDao,
    val runRemoteData: RunRemoteData,
    val runMapper: RunMapper,
    val dataFreshnessUseCase: DataFreshnessUseCase,
)
