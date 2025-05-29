package com.jdagnogo.welovemarathon.home.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.banner.GetBannerUseCase
import com.jdagnogo.welovemarathon.offer.domain.GetOfferCountUseCase
import com.jdagnogo.welovemarathon.offer.domain.GetOfferUseCase
import com.jdagnogo.welovemarathon.offer.domain.IsOfferAvailableUseCase
import com.jdagnogo.welovemarathon.offer.domain.UpdateOfferDisplayCountUseCase
import com.jdagnogo.welovemarathon.restaurant.domain.GetRestaurantUseCase

@Keep
data class HomeUseCases(
    val getBannerUseCase: GetBannerUseCase,
    val getRestaurantUseCase: GetRestaurantUseCase,
    val getOfferUseCase: GetOfferUseCase,
    val isOfferAvailableUseCase: IsOfferAvailableUseCase,
    val updateOfferDisplayCountUseCase : UpdateOfferDisplayCountUseCase,
    val getOfferCountUseCase: GetOfferCountUseCase
)
