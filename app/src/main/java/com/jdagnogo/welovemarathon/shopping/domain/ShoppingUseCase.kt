package com.jdagnogo.welovemarathon.shopping.domain

import androidx.annotation.Keep

@Keep
data class ShoppingUseCase(
    val getShoppingUseCase: GetShoppingUseCase,
    val getShoppingCategoriesUseCase: GetShoppingCategoriesUseCase,
)
