package com.jdagnogo.welovemarathon.home.di

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.home.data.BlogDao
import com.jdagnogo.welovemarathon.home.data.BlogMapper
import com.jdagnogo.welovemarathon.home.data.HomeRepository
import com.jdagnogo.welovemarathon.home.domain.GetBlogUseCase
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
    fun provideBlogMapper(): BlogMapper {
        return BlogMapper()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideBlogDao(db: WLMDatabase): BlogDao = db.getBlogDao()
}
