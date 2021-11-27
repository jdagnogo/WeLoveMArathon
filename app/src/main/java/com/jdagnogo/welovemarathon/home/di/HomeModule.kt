package com.jdagnogo.welovemarathon.home.di

import com.jdagnogo.welovemarathon.blog.domain.GetBlogUseCase
import com.jdagnogo.welovemarathon.common.banner.GetHomeBannerUseCase
import com.jdagnogo.welovemarathon.home.domain.HomeUseCases
import com.jdagnogo.welovemarathon.home.presentation.HomeReducer
import com.jdagnogo.welovemarathon.run.domain.GetRunUseCase
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
    fun provideHomeUseCases(
        getRunUseCase: GetRunUseCase,
        getBlogUseCase: GetBlogUseCase,
        getHomeBannerUseCase: GetHomeBannerUseCase,
    ) = HomeUseCases(getBlogUseCase = getBlogUseCase,
        getRunUseCase = getRunUseCase,
        getHomeBannerUseCase = getHomeBannerUseCase)

    @Provides
    @Singleton
    fun provideHomeReducer() = HomeReducer()
}
