package com.jdagnogo.welovemarathon.map.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.shopping.domain.GetShoppingCategoriesUseCase
import com.jdagnogo.welovemarathon.shopping.domain.GetShoppingUseCase

@Keep
data class MapUseCases(
    val getShoppingUseCase: GetShoppingUseCase,
    val getShoppingCategoriesUseCase: GetShoppingCategoriesUseCase,
)
