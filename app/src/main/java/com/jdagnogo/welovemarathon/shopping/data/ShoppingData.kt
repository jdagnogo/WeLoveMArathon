package com.jdagnogo.welovemarathon.shopping.data

import androidx.annotation.Keep

@Keep
data class ShoppingData(
    val shoppingDao: ShoppingDao,
    val shoppingRemoteData: ShoppingRemoteData,
    val shoppingMapper: ShoppingMapper,
)
