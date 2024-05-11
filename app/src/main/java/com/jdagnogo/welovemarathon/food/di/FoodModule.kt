package com.jdagnogo.welovemarathon.food.di

import com.jdagnogo.welovemarathon.restaurant.presentation.RestaurantReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestaurantModule {

    @Provides
    @Singleton
    fun provideRestaurantReducer() = RestaurantReducer()

}
