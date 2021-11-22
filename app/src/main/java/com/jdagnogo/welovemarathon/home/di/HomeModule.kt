package com.jdagnogo.welovemarathon.home.di

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.home.data.HomeData
import com.jdagnogo.welovemarathon.home.data.HomeRepository
import com.jdagnogo.welovemarathon.home.data.blog.BlogDao
import com.jdagnogo.welovemarathon.home.data.blog.BlogMapper
import com.jdagnogo.welovemarathon.home.data.blog.BlogRemoteData
import com.jdagnogo.welovemarathon.home.data.run.RunDao
import com.jdagnogo.welovemarathon.home.data.run.RunMapper
import com.jdagnogo.welovemarathon.home.data.run.RunRemoteData
import com.jdagnogo.welovemarathon.home.domain.GetBlogUseCase
import com.jdagnogo.welovemarathon.home.domain.GetRunUseCase
import com.jdagnogo.welovemarathon.home.domain.HomeUseCases
import com.jdagnogo.welovemarathon.home.presentation.HomeReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideGetBlogUseCase(
        repository: HomeRepository,
    ): GetBlogUseCase {
        return GetBlogUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetRunUseCase(
        repository: HomeRepository,
    ): GetRunUseCase {
        return GetRunUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideHomeUseCases(
        getRunUseCase: GetRunUseCase,
        getBlogUseCase: GetBlogUseCase,
    ) = HomeUseCases(getBlogUseCase = getBlogUseCase, getRunUseCase = getRunUseCase)

    @Provides
    @Singleton
    fun provideHomeReducer() = HomeReducer()

    @Provides
    @Singleton
    fun provideHomeData(
        blogDao: BlogDao,
        blogRemoteData: BlogRemoteData,
        blogMapper: BlogMapper,
        runDao: RunDao,
        runRemoteData: RunRemoteData,
        runMapper: RunMapper,
    ) = HomeData(blogDao, blogRemoteData, blogMapper, runDao, runRemoteData, runMapper)

    @Provides
    @Singleton
    fun provideBlogMapper(): BlogMapper {
        return BlogMapper()
    }

    @Provides
    @Singleton
    fun provideRunMapper(): RunMapper {
        return RunMapper()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideBlogDao(db: WLMDatabase): BlogDao = db.getBlogDao()

    @Singleton
    @Provides
    fun provideRunDao(db: WLMDatabase): RunDao = db.getRunDao()
}
