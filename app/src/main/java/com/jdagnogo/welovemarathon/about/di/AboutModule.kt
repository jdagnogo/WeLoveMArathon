package com.jdagnogo.welovemarathon.about.di

import com.jdagnogo.welovemarathon.about.domain.AboutUseCases
import com.jdagnogo.welovemarathon.about.presentation.AboutReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AboutModule {
    @Provides
    @Singleton
    fun provideAboutReducer() = AboutReducer()

    @Provides
    @Singleton
    fun provideAboutUseCases() = AboutUseCases()
}