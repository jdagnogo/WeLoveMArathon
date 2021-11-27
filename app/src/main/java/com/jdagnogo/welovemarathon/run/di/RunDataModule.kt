package com.jdagnogo.welovemarathon.run.di

import com.jdagnogo.welovemarathon.run.data.RunFirebaseData
import com.jdagnogo.welovemarathon.run.data.RunRemoteData
import com.jdagnogo.welovemarathon.run.data.RunRepository
import com.jdagnogo.welovemarathon.run.data.RunRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RunDataModule {
    @Binds
    abstract fun bindHomeRepository(
        runRepositoryImpl: RunRepositoryImpl,
    ): RunRepository

    @Binds
    abstract fun bindRunFirebaseData(
        runFirebaseData: RunFirebaseData,
    ): RunRemoteData
}
