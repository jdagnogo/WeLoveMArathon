package com.jdagnogo.welovemarathon.culture.di

import com.jdagnogo.welovemarathon.culture.data.CultureFirebaseData
import com.jdagnogo.welovemarathon.culture.data.CultureRemoteData
import com.jdagnogo.welovemarathon.culture.data.CultureRepository
import com.jdagnogo.welovemarathon.culture.data.CultureRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CultureDataModule {
    @Binds
    abstract fun bindCultureFirebaseData(
        CultureFirebaseData: CultureFirebaseData,
    ): CultureRemoteData

    @Binds
    abstract fun bindCultureRepository(
        CultureRepositoryImpl: CultureRepositoryImpl,
    ): CultureRepository
}
