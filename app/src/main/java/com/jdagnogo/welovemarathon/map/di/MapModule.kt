package com.jdagnogo.welovemarathon.map.di

import com.jdagnogo.welovemarathon.activities.domain.GetActivitiesCategoriesUseCase
import com.jdagnogo.welovemarathon.activities.domain.GetActivitiesUseCase
import com.jdagnogo.welovemarathon.beach.domain.GetBeachesBarUseCase
import com.jdagnogo.welovemarathon.beach.domain.GetBeachesUseCase
import com.jdagnogo.welovemarathon.food.domain.GetFoodCategoriesUseCase
import com.jdagnogo.welovemarathon.food.domain.GetFoodUseCase
import com.jdagnogo.welovemarathon.map.domain.MapUseCases
import com.jdagnogo.welovemarathon.map.viewmodel.MapReducer
import com.jdagnogo.welovemarathon.restaurant.domain.GetRestaurantUseCase
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
        getRestaurantUseCase: GetRestaurantUseCase,
        getBeachesBarUseCase: GetBeachesBarUseCase,
        getBeachesUseCase: GetBeachesUseCase,
        getActivitiesUseCase: GetActivitiesUseCase,
        getActivitiesCategoriesUseCase: GetActivitiesCategoriesUseCase
    ): MapUseCases {
        return MapUseCases(
            getShoppingCategoriesUseCase = getShoppingCategoriesUseCase,
            getShoppingUseCase = getShoppingUseCase,
            getFoodCategoriesUseCase = getFoodCategoriesUseCase,
            getRestaurantUseCase = getRestaurantUseCase,
            getBeachesBarUseCase = getBeachesBarUseCase,
            getBeachesUseCase = getBeachesUseCase,
            getActivitiesUseCase = getActivitiesUseCase,
            getActivitiesCategoriesUseCase = getActivitiesCategoriesUseCase,
        )
    }

    @Provides
    @Singleton
    fun provideMapReducer() = MapReducer()
}
