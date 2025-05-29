package com.jdagnogo.welovemarathon.activities.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class ActivitiesData(
    val dao: ActivitiesDao,
    val remoteData: ActivitiesRemoteData,
    val dataFreshnessUseCase: DataFreshnessUseCase,
    val mapper: ActivitiesMapper
)
