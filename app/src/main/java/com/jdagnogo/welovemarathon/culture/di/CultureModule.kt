package com.jdagnogo.welovemarathon.culture.di

import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import com.jdagnogo.welovemarathon.culture.data.CultureDao
import com.jdagnogo.welovemarathon.culture.data.CultureData
import com.jdagnogo.welovemarathon.culture.data.CultureMapper
import com.jdagnogo.welovemarathon.culture.data.CultureRemoteData
import com.jdagnogo.welovemarathon.culture.data.CultureRepository
import com.jdagnogo.welovemarathon.culture.domain.CultureUseCases
import com.jdagnogo.welovemarathon.culture.domain.GetCulturesUseCase
import com.jdagnogo.welovemarathon.culture.presentation.CultureReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@ExperimentalPagerApi
@Module
@InstallIn(SingletonComponent::class)
object CultureModule {

    @Provides
    @Singleton
    fun provideGetCultureUseCase(
        cultureRepository: CultureRepository,
    ): GetCulturesUseCase {
        return GetCulturesUseCase(cultureRepository)
    }

    @Provides
    @Singleton
    fun provideHomeData(
        cultureDao: CultureDao,
        cultureRemoteData: CultureRemoteData,
        cultureMapper: CultureMapper,
        dataFreshnessUseCase: DataFreshnessUseCase,
    ) = CultureData(cultureDao, cultureRemoteData, cultureMapper, dataFreshnessUseCase)

    @Provides
    @Singleton
    fun provideCultureMapper(): CultureMapper {
        return CultureMapper()
    }

    @Provides
    @Singleton
    fun providePagerState(): PagerState {
        return PagerState()
    }

    @Provides
    @Singleton
    fun provideCultureReducer() = CultureReducer()

    @Provides
    @Singleton
    fun provideCultureUseCases(
        getCulturesUseCase: GetCulturesUseCase,
    ) = CultureUseCases(
        getCulturesUseCase = getCulturesUseCase,
    )

    @Singleton
    @Provides
    fun provideCultureDao(db: WLMDatabase): CultureDao = db.getCultureDao()
}
