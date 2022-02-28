package com.jdagnogo.welovemarathon.shopping.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class ShoppingData(
    val dao: ShoppingDao,
    val remoteData: ShoppingRemoteData,
    val dataFreshnessUseCase: DataFreshnessUseCase,
    val mapper: ShoppingMapper,
)
