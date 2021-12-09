package com.jdagnogo.welovemarathon.tips.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class TipsData(
    val dao: TipsDao,
    val remoteData: TipsRemoteData,
    val mapper: TipsMapper,
    val dataFreshnessUseCase: DataFreshnessUseCase,
)
