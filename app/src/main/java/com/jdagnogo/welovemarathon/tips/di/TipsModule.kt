package com.jdagnogo.welovemarathon.tips.di

import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import com.jdagnogo.welovemarathon.tips.data.*
import com.jdagnogo.welovemarathon.tips.domain.GetTipsUseCase
import com.jdagnogo.welovemarathon.tips.presentation.TipsReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TipsModule {

    @Provides
    @Singleton
    fun provideGetTipsUseCase(
        repository: TipsRepository,
    ): GetTipsUseCase {
        return GetTipsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideTipsData(
        dao: TipsDao,
        remoteData: TipsRemoteData,
        mapper: TipsMapper,
        dataFreshnessUseCase: DataFreshnessUseCase,
    ) = TipsData(dao, remoteData, mapper, dataFreshnessUseCase)

    @Provides
    @Singleton
    fun provideTipsMapper(): TipsMapper {
        return TipsMapper()
    }

    @Provides
    @Singleton
    fun provideTipsReducer() = TipsReducer()

    @Singleton
    @Provides
    fun provideTipsDao(db: WLMDatabase): TipsDao = db.getTipsDao()
}
