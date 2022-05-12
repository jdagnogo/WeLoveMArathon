package com.jdagnogo.welovemarathon.beach.di

import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.jdagnogo.welovemarathon.beach.data.BeachDao
import com.jdagnogo.welovemarathon.beach.data.BeachData
import com.jdagnogo.welovemarathon.beach.data.BeachMapper
import com.jdagnogo.welovemarathon.beach.data.BeachRemoteData
import com.jdagnogo.welovemarathon.beach.data.BeachRepository
import com.jdagnogo.welovemarathon.beach.domain.BeachUseCases
import com.jdagnogo.welovemarathon.beach.domain.GetBeachesUseCase
import com.jdagnogo.welovemarathon.beach.domain.GetRecommendedBeachesBarsUseCase
import com.jdagnogo.welovemarathon.beach.presentation.BeachReducer
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalPagerApi
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
        dataFreshnessUseCase: DataFreshnessUseCase,
    ) = BeachData(beachDao, beachRemoteData, beachMapper, dataFreshnessUseCase)

    @Provides
    @Singleton
    fun provideBeachMapper(): BeachMapper {
        return BeachMapper()
    }

    @Provides
    @Singleton
    fun providePagerState(): PagerState {
        return PagerState()
    }

    @Provides
    @Singleton
    fun provideBeachReducer() = BeachReducer()

    @Provides
    @Singleton
    fun provideBeachUseCases(
        getBeachesUseCase: GetBeachesUseCase,
        getRecommendedBeachesBarsUseCase: GetRecommendedBeachesBarsUseCase
    ) = BeachUseCases(
        getBeachesUseCase = getBeachesUseCase,
        getRecommendedBeachesBarsUseCase = getRecommendedBeachesBarsUseCase
    )

    @Singleton
    @Provides
    fun provideBeachDao(db: WLMDatabase): BeachDao = db.getBeachDao()
}
