package com.jdagnogo.welovemarathon.culture.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class CultureData(
    val cultureDao: CultureDao,
    val cultureRemoteData: CultureRemoteData,
    val cultureMapper: CultureMapper,
    val dataFreshnessUseCase: DataFreshnessUseCase,
)
