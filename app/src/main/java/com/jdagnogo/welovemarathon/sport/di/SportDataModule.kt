package com.jdagnogo.welovemarathon.sport.di

import com.jdagnogo.welovemarathon.sport.data.SportFirebaseData
import com.jdagnogo.welovemarathon.sport.data.SportRemoteData
import com.jdagnogo.welovemarathon.sport.data.SportRepository
import com.jdagnogo.welovemarathon.sport.data.SportRepositoryIml
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SportDataModule {
    @Binds
    abstract fun bindSportRepository(
        repositoryImpl: SportRepositoryIml,
    ): SportRepository

    @Binds
    abstract fun bindSportFirebaseData(
        fFirebaseData: SportFirebaseData,
    ): SportRemoteData
}
