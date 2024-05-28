package com.jdagnogo.welovemarathon.restaurant.di

import com.jdagnogo.welovemarathon.restaurant.data.RestaurantFirebaseData
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantRemoteData
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantRepository
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RestaurantModuleData {
    @Binds
    abstract fun bindRestaurantRepository(
        impl: RestaurantRepositoryImpl,
    ): RestaurantRepository

    @Binds
    abstract fun bindBlogFirebaseData(
        firebaseData: RestaurantFirebaseData,
    ): RestaurantRemoteData
}
