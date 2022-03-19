package com.jdagnogo.welovemarathon.map.di

import com.jdagnogo.welovemarathon.map.domain.MapUseCases
import com.jdagnogo.welovemarathon.map.presentation.MapReducer
import com.jdagnogo.welovemarathon.shopping.domain.GetShoppingCategoriesUseCase
import com.jdagnogo.welovemarathon.shopping.domain.GetShoppingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapModule {

    @Provides
    @Singleton
    fun provideMapUseCases(
        getShoppingUseCase: GetShoppingUseCase,
        getShoppingCategoriesUseCase: GetShoppingCategoriesUseCase,
    ): MapUseCases {
        return MapUseCases(
            getShoppingCategoriesUseCase = getShoppingCategoriesUseCase,
            getShoppingUseCase = getShoppingUseCase,
        )
    }

    @Provides
    @Singleton
    fun provideMapReducer() = MapReducer()
}
