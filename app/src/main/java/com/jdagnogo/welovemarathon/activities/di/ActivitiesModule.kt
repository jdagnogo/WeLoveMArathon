package com.jdagnogo.welovemarathon.activities.di

import com.jdagnogo.welovemarathon.activities.data.ActivitiesDao
import com.jdagnogo.welovemarathon.activities.data.ActivitiesData
import com.jdagnogo.welovemarathon.activities.data.ActivitiesMapper
import com.jdagnogo.welovemarathon.activities.data.ActivitiesRemoteData
import com.jdagnogo.welovemarathon.activities.data.ActivitiesRepository
import com.jdagnogo.welovemarathon.activities.domain.ActivitiesUseCase
import com.jdagnogo.welovemarathon.activities.domain.GetActivitiesCategoriesUseCase
import com.jdagnogo.welovemarathon.activities.domain.GetActivitiesTagUseCase
import com.jdagnogo.welovemarathon.activities.domain.GetActivitiesUseCase
import com.jdagnogo.welovemarathon.activities.presentation.ActivitiesReducer
import com.jdagnogo.welovemarathon.common.banner.GetBannerUseCase
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ActivitiesModule {

    @Provides
    @Singleton
    fun provideGetActivitiesUseCase(
        repository: ActivitiesRepository,
    ): GetActivitiesUseCase {
        return GetActivitiesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetActivitiesTagUseCase(
        repository: ActivitiesRepository,
    ): GetActivitiesTagUseCase {
        return GetActivitiesTagUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideActivitiesData(
        activitiesDao: ActivitiesDao,
        activitiesRemoteData: ActivitiesRemoteData,
        activitiesMapper: ActivitiesMapper,
        dataFreshnessUseCase: DataFreshnessUseCase,
    ) = ActivitiesData(activitiesDao, activitiesRemoteData, dataFreshnessUseCase, activitiesMapper)

    @Provides
    @Singleton
    fun provideActivitiesMapper(): ActivitiesMapper {
        return ActivitiesMapper()
    }

    @Singleton
    @Provides
    fun provideActivitiesDao(db: WLMDatabase): ActivitiesDao = db.getActivitiesDao()

    @Provides
    @Singleton
    fun provideActivitiesUseCase(
        getActivitiesUseCase: GetActivitiesUseCase,
        getActivitiesCategoriesUseCase: GetActivitiesCategoriesUseCase,
        getActivitiesTagUseCase: GetActivitiesTagUseCase,
        getBannerUseCase: GetBannerUseCase,
    ) = ActivitiesUseCase(
        getActivitiesUseCase = getActivitiesUseCase,
        getActivitiesCategoriesUseCase = getActivitiesCategoriesUseCase,
        getBannerUseCase = getBannerUseCase,
        getActivitiesTagUseCase = getActivitiesTagUseCase,
    )

    @Provides
    @Singleton
    fun provideActivitiesReducer() = ActivitiesReducer()

    @Provides
    @Singleton
    fun provideGetActivitiesCategoriesUseCase(repository: ActivitiesRepository) =
        GetActivitiesCategoriesUseCase(repository)
}
