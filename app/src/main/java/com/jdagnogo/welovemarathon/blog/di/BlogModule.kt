package com.jdagnogo.welovemarathon.blog.di

import com.jdagnogo.welovemarathon.blog.data.*
import com.jdagnogo.welovemarathon.blog.domain.GetBlogUseCase
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BlogModule {

    @Provides
    @Singleton
    fun provideGetBlogUseCase(
        blogRepository: BlogRepository,
    ): GetBlogUseCase {
        return GetBlogUseCase(blogRepository)
    }

    @Provides
    @Singleton
    fun provideHomeData(
        blogDao: BlogDao,
        blogRemoteData: BlogRemoteData,
        blogMapper: BlogMapper,
    ) = BlogData(blogDao, blogRemoteData, blogMapper)

    @Provides
    @Singleton
    fun provideBlogMapper(): BlogMapper {
        return BlogMapper()
    }

    @Singleton
    @Provides
    fun provideBlogDao(db: WLMDatabase): BlogDao = db.getBlogDao()
}
