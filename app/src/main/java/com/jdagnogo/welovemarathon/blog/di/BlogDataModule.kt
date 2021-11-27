package com.jdagnogo.welovemarathon.blog.di

import com.jdagnogo.welovemarathon.blog.data.BlogFirebaseData
import com.jdagnogo.welovemarathon.blog.data.BlogRemoteData
import com.jdagnogo.welovemarathon.blog.data.BlogRepository
import com.jdagnogo.welovemarathon.blog.data.BlogRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BlogDataModule {
    @Binds
    abstract fun bindBlogFirebaseData(
        blogFirebaseData: BlogFirebaseData,
    ): BlogRemoteData

    @Binds
    abstract fun bindBlogRepository(
        blogRepositoryImpl: BlogRepositoryImpl,
    ): BlogRepository
}
