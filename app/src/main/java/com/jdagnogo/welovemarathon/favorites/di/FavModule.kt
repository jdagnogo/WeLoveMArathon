package com.jdagnogo.welovemarathon.favorites.di

import com.jdagnogo.welovemarathon.favorites.presentation.FavReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavModule {

    @Provides
    @Singleton
    fun provideFavReducer() = FavReducer()
}