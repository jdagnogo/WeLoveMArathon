package com.jdagnogo.welovemarathon.about.di

import com.jdagnogo.welovemarathon.about.data.*
import com.jdagnogo.welovemarathon.about.domain.AboutUseCases
import com.jdagnogo.welovemarathon.about.domain.GetAboutUseCase
import com.jdagnogo.welovemarathon.about.presentation.AboutReducer
import com.jdagnogo.welovemarathon.beach.data.BeachDao
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
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
    fun provideAboutUseCases(
        getAboutUseCase: GetAboutUseCase
    ) = AboutUseCases(getAboutUseCase)

    @Provides
    @Singleton
    fun provideGetAboutUseCase(
        repository: AboutRepository
    ) = GetAboutUseCase(repository)

    @Provides
    @Singleton
    fun provideHomeData(
        aboutDao: AboutDao,
        aboutRemoteData: AboutRemoteData,
        aboutMapper: AboutMapper,
        dataFreshnessUseCase: DataFreshnessUseCase,
    ) = AboutData(aboutDao, aboutRemoteData, aboutMapper, dataFreshnessUseCase)

    @Provides
    @Singleton
    fun provideAboutMapper(): AboutMapper {
        return AboutMapper()
    }

    @Singleton
    @Provides
    fun provideAboutDao(db: WLMDatabase): AboutDao = db.getAboutDao()
}