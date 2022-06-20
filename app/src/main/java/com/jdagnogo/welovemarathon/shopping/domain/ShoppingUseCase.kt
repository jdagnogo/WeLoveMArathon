package com.jdagnogo.welovemarathon.shopping.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.banner.GetBannerUseCase
import com.jdagnogo.welovemarathon.common.like.domain.FavUseCase

@Keep
data class ShoppingUseCase(
    val getShoppingUseCase: GetShoppingUseCase,
    val getShoppingCategoriesUseCase: GetShoppingCategoriesUseCase,
    val getShoppingTagUseCase: GetShoppingTagUseCase,
    val favUseCase: FavUseCase,
    val getBannerUseCase: GetBannerUseCase
)
