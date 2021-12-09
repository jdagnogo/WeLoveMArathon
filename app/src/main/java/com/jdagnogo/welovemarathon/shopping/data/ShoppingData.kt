package com.jdagnogo.welovemarathon.shopping.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase

@Keep
data class ShoppingData(
    val shoppingDao: ShoppingDao,
    val shoppingRemoteData: ShoppingRemoteData,
    val dataFreshnessUseCase: DataFreshnessUseCase,
    val shoppingMapper: ShoppingMapper,
)
