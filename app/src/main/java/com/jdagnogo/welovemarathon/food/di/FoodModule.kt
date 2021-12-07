package com.jdagnogo.welovemarathon.food.di

import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.food.data.FoodData
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantDao
import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantMapper
import com.jdagnogo.welovemarathon.food.domain.FoodUseCase
import com.jdagnogo.welovemarathon.food.domain.restaurant.GetCoffeeUseCase
import com.jdagnogo.welovemarathon.food.domain.restaurant.GetDessertsUseCase
import com.jdagnogo.welovemarathon.food.domain.restaurant.GetRestaurantUseCase
import com.jdagnogo.welovemarathon.food.domain.restaurant.RestaurantRemoteData
import com.jdagnogo.welovemarathon.food.presentation.FoodReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FoodModule {

    @Provides
    @Singleton
    fun provideGetRestaurantUseCase(
        repository: FoodRepository,
    ): GetRestaurantUseCase {
        return GetRestaurantUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetDessertsUseCase(
        repository: FoodRepository,
    ): GetDessertsUseCase {
        return GetDessertsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCoffeeUseCase(
        repository: FoodRepository,
    ): GetCoffeeUseCase {
        return GetCoffeeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFoodUseCases(
        getRestaurantUseCase: GetRestaurantUseCase,
        getCoffeeUseCase: GetCoffeeUseCase,
        getDessertsUseCase: GetDessertsUseCase,
    ) = FoodUseCase(getRestaurantUseCase, getCoffeeUseCase, getDessertsUseCase)

    @Provides
    @Singleton
    fun provideFoodReducer() = FoodReducer()

    @Provides
    @Singleton
    fun provideFoodData(
        restaurantDao: RestaurantDao,
        restaurantMapper: RestaurantMapper,
        restaurantRemoteData: RestaurantRemoteData,
    ) = FoodData(restaurantDao, restaurantMapper, restaurantRemoteData)

    @Provides
    @Singleton
    fun provideRestaurantMapper(): RestaurantMapper {
        return RestaurantMapper()
    }

    @Singleton
    @Provides
    fun provideRestaurantDao(db: WLMDatabase): RestaurantDao = db.getRestaurantDao()
}
