package com.jdagnogo.welovemarathon.food.di

import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.food.data.FoodData
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantDao
import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantMapper
import com.jdagnogo.welovemarathon.food.domain.FoodUseCase
import com.jdagnogo.welovemarathon.food.domain.restaurant.GetFoodOthersUseCase
import com.jdagnogo.welovemarathon.food.domain.restaurant.GetFoodRecommendedUseCase
import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantRemoteData
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
    fun provideGetFoodRecommendedUseCase(
        repository: FoodRepository,
    ): GetFoodRecommendedUseCase {
        return GetFoodRecommendedUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetFoodOthersUseCase(
        repository: FoodRepository,
    ): GetFoodOthersUseCase {
        return GetFoodOthersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFoodUseCases(
        getFoodRecommendedUseCase: GetFoodRecommendedUseCase,
        getFoodOthersUseCase: GetFoodOthersUseCase,
    ) = FoodUseCase(getFoodRecommendedUseCase, getFoodOthersUseCase)

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
