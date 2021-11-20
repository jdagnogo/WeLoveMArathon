package com.jdagnogo.welovemarathon.home.di

import com.jdagnogo.welovemarathon.home.data.blog.BlogFirebaseData
import com.jdagnogo.welovemarathon.home.data.blog.BlogRemoteData
import com.jdagnogo.welovemarathon.home.data.HomeRepository
import com.jdagnogo.welovemarathon.home.data.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeDataModule {
    @Binds
    abstract fun bindHomeRepository(
        homeRepositoryImpl: HomeRepositoryImpl,
    ): HomeRepository

    @Binds
    abstract fun bindBlogFirebaseData(
        blogFirebaseData: BlogFirebaseData,
    ): BlogRemoteData
}
