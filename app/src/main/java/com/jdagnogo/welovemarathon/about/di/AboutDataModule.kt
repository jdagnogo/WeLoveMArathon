package com.jdagnogo.welovemarathon.about.di

import com.jdagnogo.welovemarathon.about.data.AboutFirebaseData
import com.jdagnogo.welovemarathon.about.data.AboutRemoteData
import com.jdagnogo.welovemarathon.about.data.AboutRepository
import com.jdagnogo.welovemarathon.about.data.AboutRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AboutDataModule {
    @Binds
    abstract fun bindAboutFirebaseData(
        AboutFirebaseData: AboutFirebaseData,
    ): AboutRemoteData

    @Binds
    abstract fun bindAboutRepository(
        AboutRepositoryImpl: AboutRepositoryImpl,
    ): AboutRepository
}