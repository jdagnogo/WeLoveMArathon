package com.jdagnogo.welovemarathon.map.di

import com.jdagnogo.welovemarathon.beach.domain.GetBeachesBarUseCase
import com.jdagnogo.welovemarathon.beach.domain.GetBeachesUseCase
import com.jdagnogo.welovemarathon.food.domain.GetFoodCategoriesUseCase
import com.jdagnogo.welovemarathon.food.domain.GetFoodUseCase
import com.jdagnogo.welovemarathon.map.domain.MapUseCases
import com.jdagnogo.welovemarathon.map.viewmodel.MapReducer
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
        getFoodCategoriesUseCase: GetFoodCategoriesUseCase,
        getFoodUseCase: GetFoodUseCase,
        getBeachesBarUseCase: GetBeachesBarUseCase,
        getBeachesUseCase: GetBeachesUseCase,
    ): MapUseCases {
        return MapUseCases(
            getShoppingCategoriesUseCase = getShoppingCategoriesUseCase,
            getShoppingUseCase = getShoppingUseCase,
            getFoodCategoriesUseCase = getFoodCategoriesUseCase,
            getFoodUseCase = getFoodUseCase,
            getBeachesBarUseCase = getBeachesBarUseCase,
            getBeachesUseCase = getBeachesUseCase,
        )
    }

    @Provides
    @Singleton
    fun provideMapReducer() = MapReducer()
}
