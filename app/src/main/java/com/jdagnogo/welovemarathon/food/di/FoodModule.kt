package com.jdagnogo.welovemarathon.food.di

import com.jdagnogo.welovemarathon.common.banner.GetBannerUseCase
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import com.jdagnogo.welovemarathon.common.like.domain.FavUseCase
import com.jdagnogo.welovemarathon.food.data.FoodDao
import com.jdagnogo.welovemarathon.food.data.FoodData
import com.jdagnogo.welovemarathon.food.data.FoodMapper
import com.jdagnogo.welovemarathon.food.data.FoodRemoteData
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import com.jdagnogo.welovemarathon.food.domain.FoodUseCase
import com.jdagnogo.welovemarathon.food.domain.GetFoodCategoriesUseCase
import com.jdagnogo.welovemarathon.food.domain.GetFoodTagUseCase
import com.jdagnogo.welovemarathon.food.domain.GetFoodUseCase
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
    fun provideGetFoodUseCase(
        repository: FoodRepository,
    ): GetFoodUseCase {
        return GetFoodUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetFoodTagUseCase(
        repository: FoodRepository,
    ): GetFoodTagUseCase {
        return GetFoodTagUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFoodData(
        foodDao: FoodDao,
        foodRemoteData: FoodRemoteData,
        foodMapper: FoodMapper,
        dataFreshnessUseCase: DataFreshnessUseCase,
    ) = FoodData(foodDao, foodRemoteData, dataFreshnessUseCase, foodMapper)

    @Provides
    @Singleton
    fun provideFoodMapper(): FoodMapper {
        return FoodMapper()
    }

    @Singleton
    @Provides
    fun provideFoodDao(db: WLMDatabase): FoodDao = db.getFoodDao()

    @Provides
    @Singleton
    fun provideFoodUseCase(
        getFoodUseCase: GetFoodUseCase,
        getFoodCategoriesUseCase: GetFoodCategoriesUseCase,
        getFoodTagUseCase: GetFoodTagUseCase,
        getBannerUseCase: GetBannerUseCase,
        favUseCase: FavUseCase,
    ) = FoodUseCase(
        getFoodUseCase = getFoodUseCase,
        getFoodCategoriesUseCase = getFoodCategoriesUseCase,
        getBannerUseCase = getBannerUseCase,
        getFoodTagUseCase = getFoodTagUseCase,
        favUseCase = favUseCase,
    )

    @Provides
    @Singleton
    fun provideFoodReducer() = FoodReducer()

    @Provides
    @Singleton
    fun provideGetFoodCategoriesUseCase(repository: FoodRepository) =
        GetFoodCategoriesUseCase(repository)
}
