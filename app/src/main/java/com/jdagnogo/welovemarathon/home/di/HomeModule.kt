package com.jdagnogo.welovemarathon.home.di

import com.jdagnogo.welovemarathon.beach.domain.GetShortListBeachesUseCase
import com.jdagnogo.welovemarathon.common.banner.GetHomeBannerUseCase
import com.jdagnogo.welovemarathon.home.domain.HomeUseCases
import com.jdagnogo.welovemarathon.home.presentation.HomeReducer
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
        bannerUseCase: GetHomeBannerUseCase,
        getshortlistbeachesusecase: GetShortListBeachesUseCase,
    ): HomeUseCases =
        HomeUseCases(bannerUseCase, getshortlistbeachesusecase)
}
