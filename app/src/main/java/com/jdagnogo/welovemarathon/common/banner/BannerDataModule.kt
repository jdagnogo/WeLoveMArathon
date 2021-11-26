package com.jdagnogo.welovemarathon.common.banner

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BannerDataModule {
    @Binds
    abstract fun bindBannerRepository(
        repo: BannerRepositoryImpl,
    ): BannerRepository

    @Binds
    abstract fun bindBannerRemoteData(
        firebase: BannerFirebaseData,
    ): BannerRemoteData
}
