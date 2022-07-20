package com.jdagnogo.welovemarathon.about.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class AboutData(
    val aboutDao: AboutDao,
    val aboutRemoteData: AboutRemoteData,
    val aboutMapper: AboutMapper,
    val dataFreshnessUseCase: DataFreshnessUseCase,
)
