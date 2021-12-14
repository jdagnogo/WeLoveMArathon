package com.jdagnogo.welovemarathon.sport.di

import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import com.jdagnogo.welovemarathon.sport.data.*
import com.jdagnogo.welovemarathon.sport.domain.GetSportCategoriesUseCase
import com.jdagnogo.welovemarathon.sport.domain.GetSportsUseCase
import com.jdagnogo.welovemarathon.sport.domain.SportUseCase
import com.jdagnogo.welovemarathon.sport.presentation.SportReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SportModule {
    @Provides
    @Singleton
    fun provideSportReducer() = SportReducer()

    @Provides
    @Singleton
    fun provideGetSportCategoriesUseCase(repository: SportRepository) =
        GetSportCategoriesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSportsUseCase(repository: SportRepository) = GetSportsUseCase(repository)

    @Provides
    @Singleton
    fun provideSportUseCase(
        getSportsUseCase: GetSportsUseCase,
        getSportCategoriesUseCase: GetSportCategoriesUseCase,
    ) = SportUseCase(getSportCategoriesUseCase = getSportCategoriesUseCase,
        getSportsUseCase = getSportsUseCase)

    @Provides
    @Singleton
    fun provideSportData(
        dao: SportDao,
        mapper: SportMapper,
        remoteData: SportRemoteData,
        dataFreshnessUseCase: DataFreshnessUseCase,
    ) = SportData(dao, mapper, remoteData, dataFreshnessUseCase)

    @Provides
    @Singleton
    fun provideSportMapper(): SportMapper {
        return SportMapper()
    }

    @Singleton
    @Provides
    fun provideSportDao(db: WLMDatabase): SportDao = db.getSportDao()
}
