package com.jdagnogo.welovemarathon.wine.di

import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import com.jdagnogo.welovemarathon.wine.data.*
import com.jdagnogo.welovemarathon.wine.domain.GetWineInfoUseCase
import com.jdagnogo.welovemarathon.wine.domain.GetWineSocialUseCase
import com.jdagnogo.welovemarathon.wine.domain.GetWineTourUseCase
import com.jdagnogo.welovemarathon.wine.domain.WineUseCases
import com.jdagnogo.welovemarathon.wine.presentation.WineReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WineModule {

    @Provides
    @Singleton
    fun provideGetWineTourUseCase(
        repository: WineRepository,
    ): GetWineTourUseCase {
        return GetWineTourUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideWineUseCases(
        getWineTourUseCase: GetWineTourUseCase,
        getWineSocialUseCase: GetWineSocialUseCase,
        getWineInfoUseCase: GetWineInfoUseCase,
    ): WineUseCases {
        return WineUseCases(
            getWineSocialUseCase = getWineSocialUseCase,
            getWineTourUseCase = getWineTourUseCase,
            getWineInfoUseCase = getWineInfoUseCase,
        )
    }

    @Provides
    @Singleton
    fun provideGetWineSocialUseCase(
        repository: WineRepository,
    ): GetWineSocialUseCase {
        return GetWineSocialUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetWineInfoUseCaseUseCase(
        repository: WineRepository,
    ): GetWineInfoUseCase {
        return GetWineInfoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideWineData(
        dao: WineDao,
        remoteData: WineRemoteData,
        mapper: WineMapper,
        dataFreshnessUseCase: DataFreshnessUseCase,
    ) = WineData(dao, remoteData, dataFreshnessUseCase, mapper)

    @Provides
    @Singleton
    fun provideWineMapper(): WineMapper {
        return WineMapper()
    }

    @Provides
    @Singleton
    fun provideWineReducer() = WineReducer()

    @Singleton
    @Provides
    fun provideWineDao(db: WLMDatabase): WineDao = db.getWineDao()
}
