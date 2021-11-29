package com.jdagnogo.welovemarathon.beach.di

import com.jdagnogo.welovemarathon.beach.data.BeachFirebaseData
import com.jdagnogo.welovemarathon.beach.data.BeachRemoteData
import com.jdagnogo.welovemarathon.beach.data.BeachRepository
import com.jdagnogo.welovemarathon.beach.data.BeachRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BeachDataModule {
    @Binds
    abstract fun bindBeachFirebaseData(
        BeachFirebaseData: BeachFirebaseData,
    ): BeachRemoteData

    @Binds
    abstract fun bindBeachRepository(
        BeachRepositoryImpl: BeachRepositoryImpl,
    ): BeachRepository
}
