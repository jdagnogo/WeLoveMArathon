package com.jdagnogo.welovemarathon.restaurant.di

import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import com.jdagnogo.welovemarathon.common.like.domain.FavUseCase
import com.jdagnogo.welovemarathon.food.domain.GetFoodCategoriesUseCase
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantDao
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantData
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantMapper
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantRemoteData
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantRepository
import com.jdagnogo.welovemarathon.restaurant.domain.GetRestaurantFilterUseCase
import com.jdagnogo.welovemarathon.restaurant.domain.GetRestaurantUseCase
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantUseCase
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
    fun provideGetRestaurantUseCase(
        repository: RestaurantRepository,
    ): GetRestaurantUseCase {
        return GetRestaurantUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetRestaurantFilterUseCase(
        repository: RestaurantRepository,
    ): GetRestaurantFilterUseCase {
        return GetRestaurantFilterUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideRestaurantData(
        foodDao: RestaurantDao,
        foodRemoteData: RestaurantRemoteData,
        foodMapper: RestaurantMapper,
        dataFreshnessUseCase: DataFreshnessUseCase,
    ) = RestaurantData(foodDao, foodRemoteData, dataFreshnessUseCase, foodMapper)

    @Provides
    @Singleton
    fun provideRestaurantMapper(): RestaurantMapper {
        return RestaurantMapper()
    }

    @Singleton
    @Provides
    fun provideRestaurantDao(db: WLMDatabase): RestaurantDao = db.getRestaurantDao()

    @Provides
    @Singleton
    fun provideRestaurantUseCase(
        getRestaurantUseCase: GetRestaurantUseCase,
        getFoodCategoriesUseCase: GetFoodCategoriesUseCase,
        getRestaurantFilterUseCase: GetRestaurantFilterUseCase,
        favUseCase: FavUseCase,
    ) = RestaurantUseCase(
        getRestaurantUseCase = getRestaurantUseCase,
        getFoodCategoriesUseCase = getFoodCategoriesUseCase,
        getRestaurantFilterUseCase = getRestaurantFilterUseCase,
        favUseCase = favUseCase,
    )

    @Provides
    @Singleton
    fun provideRestaurantReducer() = RestaurantReducer()

}
