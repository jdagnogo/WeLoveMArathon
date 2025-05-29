package com.jdagnogo.welovemarathon.home.di

import com.jdagnogo.welovemarathon.common.banner.GetBannerUseCase
import com.jdagnogo.welovemarathon.home.domain.HomeUseCases
import com.jdagnogo.welovemarathon.home.presentation.HomeReducer
import com.jdagnogo.welovemarathon.offer.domain.GetOfferCountUseCase
import com.jdagnogo.welovemarathon.offer.domain.GetOfferUseCase
import com.jdagnogo.welovemarathon.offer.domain.IsOfferAvailableUseCase
import com.jdagnogo.welovemarathon.offer.domain.UpdateOfferDisplayCountUseCase
import com.jdagnogo.welovemarathon.restaurant.domain.GetRestaurantUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Provides
    @Singleton
    fun provideHomeReducer() = HomeReducer()

    @Singleton
    @Provides
    fun provideRunDao(
        bannerUseCase: GetBannerUseCase,
        getRestaurantUseCase: GetRestaurantUseCase,
        getOfferUseCase: GetOfferUseCase,
        isOfferAvailableUseCase: IsOfferAvailableUseCase,
        updateOfferDisplayCountUseCase : UpdateOfferDisplayCountUseCase,
        getOfferCountUseCase: GetOfferCountUseCase,
    ): HomeUseCases =
        HomeUseCases(
            bannerUseCase,
            getRestaurantUseCase,
            getOfferUseCase,
            isOfferAvailableUseCase = isOfferAvailableUseCase,
            updateOfferDisplayCountUseCase =updateOfferDisplayCountUseCase,
            getOfferCountUseCase = getOfferCountUseCase,
        )
}
