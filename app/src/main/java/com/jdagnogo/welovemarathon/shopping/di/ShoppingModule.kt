package com.jdagnogo.welovemarathon.shopping.di

import com.jdagnogo.welovemarathon.common.banner.GetBannerUseCase
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import com.jdagnogo.welovemarathon.common.like.domain.FavUseCase
import com.jdagnogo.welovemarathon.shopping.data.ShoppingDao
import com.jdagnogo.welovemarathon.shopping.data.ShoppingData
import com.jdagnogo.welovemarathon.shopping.data.ShoppingMapper
import com.jdagnogo.welovemarathon.shopping.data.ShoppingRemoteData
import com.jdagnogo.welovemarathon.shopping.data.ShoppingRepository
import com.jdagnogo.welovemarathon.shopping.domain.GetShoppingCategoriesUseCase
import com.jdagnogo.welovemarathon.shopping.domain.GetShoppingTagUseCase
import com.jdagnogo.welovemarathon.shopping.domain.GetShoppingUseCase
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingUseCase
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShoppingModule {

    @Provides
    @Singleton
    fun provideGetShoppingUseCase(
        repository: ShoppingRepository,
    ): GetShoppingUseCase {
        return GetShoppingUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetShoppingTagUseCase(
        repository: ShoppingRepository,
    ): GetShoppingTagUseCase {
        return GetShoppingTagUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideShoppingData(
        shoppingDao: ShoppingDao,
        shoppingRemoteData: ShoppingRemoteData,
        shoppingMapper: ShoppingMapper,
        dataFreshnessUseCase: DataFreshnessUseCase,
    ) = ShoppingData(shoppingDao, shoppingRemoteData, dataFreshnessUseCase, shoppingMapper)

    @Provides
    @Singleton
    fun provideShoppingMapper(): ShoppingMapper {
        return ShoppingMapper()
    }

    @Singleton
    @Provides
    fun provideShoppingDao(db: WLMDatabase): ShoppingDao = db.getShoppingDao()

    @Provides
    @Singleton
    fun provideShoppingUseCase(
        getShoppingUseCase: GetShoppingUseCase,
        getShoppingCategoriesUseCase: GetShoppingCategoriesUseCase,
        getShoppingTagUseCase: GetShoppingTagUseCase,
        getBannerUseCase: GetBannerUseCase,
        favUseCase: FavUseCase,
    ) = ShoppingUseCase(
        getShoppingUseCase = getShoppingUseCase,
        getShoppingCategoriesUseCase = getShoppingCategoriesUseCase,
        getBannerUseCase = getBannerUseCase,
        favUseCase = favUseCase,
        getShoppingTagUseCase = getShoppingTagUseCase,
    )

    @Provides
    @Singleton
    fun provideShoppingReducer() = ShoppingReducer()

    @Provides
    @Singleton
    fun provideGetShoppingCategoriesUseCase(repository: ShoppingRepository) =
        GetShoppingCategoriesUseCase(repository)
}
