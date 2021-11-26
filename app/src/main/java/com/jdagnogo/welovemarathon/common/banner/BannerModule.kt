package com.jdagnogo.welovemarathon.common.banner

import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BannerModule {
    @Provides
    @Singleton
    fun provideGetHomeBannerUseCase(
        repository: BannerRepository,
    ): GetHomeBannerUseCase {
        return GetHomeBannerUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideBlogDao(db: WLMDatabase): BannerDao = db.getBannerDao()
}
