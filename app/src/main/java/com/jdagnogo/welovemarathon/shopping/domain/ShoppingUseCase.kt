package com.jdagnogo.welovemarathon.shopping.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.banner.GetBannerUseCase

@Keep
data class ShoppingUseCase(
    val getShoppingUseCase: GetShoppingUseCase,
    val getShoppingCategoriesUseCase: GetShoppingCategoriesUseCase,
    val getShoppingTagUseCase: GetShoppingTagUseCase,
    val getBannerUseCase: GetBannerUseCase
)
