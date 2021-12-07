package com.jdagnogo.welovemarathon.food.di

import com.jdagnogo.welovemarathon.food.data.FoodRepository
import com.jdagnogo.welovemarathon.food.data.FoodRepositoryIml
import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantFirebaseData
import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantRemoteData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FoodModuleData {
    @Binds
    abstract fun bindFoodRepository(
        foodRepositoryImpl: FoodRepositoryIml,
    ): FoodRepository

    @Binds
    abstract fun bindBlogFirebaseData(
        restaurantFirebaseData: RestaurantFirebaseData,
    ): RestaurantRemoteData
}
