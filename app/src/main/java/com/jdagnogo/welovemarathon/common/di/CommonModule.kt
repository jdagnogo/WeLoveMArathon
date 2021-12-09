package com.jdagnogo.welovemarathon.common.di

import com.jdagnogo.welovemarathon.common.data.DataFreshnessDao
import com.jdagnogo.welovemarathon.common.data.DataFreshnessRepository
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Provides
    @Singleton
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun providesDataFreshnessUseCase(
        dataFreshnessRepository: DataFreshnessRepository,
    ): DataFreshnessUseCase = DataFreshnessUseCase(dataFreshnessRepository)

    @Provides
    @Singleton
    fun providesDataFreshnessDao(
        db: WLMDatabase,
    ): DataFreshnessDao = db.getDataFreshnessDao()
}
