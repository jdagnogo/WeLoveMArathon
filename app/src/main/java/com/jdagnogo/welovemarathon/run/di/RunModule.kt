package com.jdagnogo.welovemarathon.run.di

import com.jdagnogo.welovemarathon.blog.domain.GetBlogUseCase
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import com.jdagnogo.welovemarathon.run.data.*
import com.jdagnogo.welovemarathon.run.domain.GetRunUseCase
import com.jdagnogo.welovemarathon.run.domain.RunUseCases
import com.jdagnogo.welovemarathon.run.presentation.RunReducer
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
    fun provideRunData(
        runDao: RunDao,
        runRemoteData: RunRemoteData,
        runMapper: RunMapper,
        dataFreshnessUseCase: DataFreshnessUseCase,
    ) = RunData(runDao, runRemoteData, runMapper, dataFreshnessUseCase)

    @Provides
    @Singleton
    fun provideRunMapper(): RunMapper {
        return RunMapper()
    }

    @Singleton
    @Provides
    fun provideRunDao(db: WLMDatabase): RunDao = db.getRunDao()

    @Provides
    @Singleton
    fun provideRunUseCases(
        getRunUseCase: GetRunUseCase,
        getBlogUseCase: GetBlogUseCase,
    ) = RunUseCases(getBlogUseCase = getBlogUseCase,
        getRunUseCase = getRunUseCase)

    @Provides
    @Singleton
    fun provideRunReducer() = RunReducer()
}
