package com.jdagnogo.welovemarathon.run.di

import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.run.data.*
import com.jdagnogo.welovemarathon.run.domain.GetRunUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RunModule {

    @Provides
    @Singleton
    fun provideGetRunUseCase(
        repository: RunRepository,
    ): GetRunUseCase {
        return GetRunUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideHomeData(
        runDao: RunDao,
        runRemoteData: RunRemoteData,
        runMapper: RunMapper,
    ) = RunData(runDao, runRemoteData, runMapper)

    @Provides
    @Singleton
    fun provideRunMapper(): RunMapper {
        return RunMapper()
    }

    @Singleton
    @Provides
    fun provideRunDao(db: WLMDatabase): RunDao = db.getRunDao()
}
