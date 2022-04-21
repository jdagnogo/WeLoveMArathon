package com.jdagnogo.welovemarathon.food.di

import com.jdagnogo.welovemarathon.food.data.FoodFirebaseData
import com.jdagnogo.welovemarathon.food.data.FoodRemoteData
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import com.jdagnogo.welovemarathon.food.data.FoodRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FoodModuleData {
    @Binds
    abstract fun bindFoodRepository(
        foodRepositoryImpl: FoodRepositoryImpl,
    ): FoodRepository

    @Binds
    abstract fun bindBlogFirebaseData(
        restaurantFirebaseData: FoodFirebaseData,
    ): FoodRemoteData
}
