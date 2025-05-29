package com.jdagnogo.welovemarathon.tips.di

import com.jdagnogo.welovemarathon.tips.data.TipsFirebaseData
import com.jdagnogo.welovemarathon.tips.data.TipsRemoteData
import com.jdagnogo.welovemarathon.tips.data.TipsRepository
import com.jdagnogo.welovemarathon.tips.data.TipsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TipsDataModule {
    @Binds
    abstract fun bindTipsFirebaseData(
        TipsFirebaseData: TipsFirebaseData,
    ): TipsRemoteData

    @Binds
    abstract fun bindTipsRepository(
        TipsRepositoryImpl: TipsRepositoryImpl,
    ): TipsRepository
}
