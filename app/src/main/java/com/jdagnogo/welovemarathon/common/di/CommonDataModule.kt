package com.jdagnogo.welovemarathon.common.di

import com.jdagnogo.welovemarathon.common.data.DataFreshnessRepository
import com.jdagnogo.welovemarathon.common.data.DataFreshnessRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonDataModule {
    @Binds
    abstract fun bindDataFreshnessRepository(
        dataFreshnessRepositoryImpl: DataFreshnessRepositoryImpl,
    ): DataFreshnessRepository
}
