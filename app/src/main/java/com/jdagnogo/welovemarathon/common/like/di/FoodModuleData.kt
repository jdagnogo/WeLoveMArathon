package com.jdagnogo.welovemarathon.common.like.di

import com.jdagnogo.welovemarathon.common.like.data.FavRepository
import com.jdagnogo.welovemarathon.common.like.data.FavRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavModuleData {
    @Binds
    abstract fun bindFoodRepository(
        favRepositoryImpl: FavRepositoryImpl,
    ): FavRepository
}
