package com.jdagnogo.welovemarathon.wine.di

import com.jdagnogo.welovemarathon.wine.data.WineFirebaseData
import com.jdagnogo.welovemarathon.wine.data.WineRemoteData
import com.jdagnogo.welovemarathon.wine.data.WineRepository
import com.jdagnogo.welovemarathon.wine.data.WineRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WineDataModule {
    @Binds
    abstract fun bindWineFirebaseData(
        WineFirebaseData: WineFirebaseData,
    ): WineRemoteData

    @Binds
    abstract fun bindWineRepository(
        wineRepositoryImpl: WineRepositoryImpl,
    ): WineRepository
}
