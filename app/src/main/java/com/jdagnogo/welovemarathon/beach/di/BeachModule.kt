package com.jdagnogo.welovemarathon.beach.di

import com.jdagnogo.welovemarathon.beach.data.*
import com.jdagnogo.welovemarathon.beach.domain.GetBeachesUseCase
import com.jdagnogo.welovemarathon.beach.presentation.BeachReducer
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BeachModule {

    @Provides
    @Singleton
    fun provideGetBeachUseCase(
        beachRepository: BeachRepository,
    ): GetBeachesUseCase {
        return GetBeachesUseCase(beachRepository)
    }

    @Provides
    @Singleton
    fun provideHomeData(
        beachDao: BeachDao,
        beachRemoteData: BeachRemoteData,
        beachMapper: BeachMapper,
    ) = BeachData(beachDao, beachRemoteData, beachMapper)

    @Provides
    @Singleton
    fun provideBeachMapper(): BeachMapper {
        return BeachMapper()
    }

    @Provides
    @Singleton
    fun provideBeachReducer() = BeachReducer()

    @Singleton
    @Provides
    fun provideBeachDao(db: WLMDatabase): BeachDao = db.getBeachDao()
}
