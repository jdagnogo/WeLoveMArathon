package com.jdagnogo.welovemarathon.shopping.di

import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.shopping.data.*
import com.jdagnogo.welovemarathon.shopping.domain.GetShoppingUseCase
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShoppingModule {

    @Provides
    @Singleton
    fun provideGetShoppingUseCase(
        repository: ShoppingRepository,
        scope: CoroutineScope,
    ): GetShoppingUseCase {
        return GetShoppingUseCase(repository, scope)
    }

    @Provides
    @Singleton
    fun provideShoppingData(
        shoppingDao: ShoppingDao,
        shoppingRemoteData: ShoppingRemoteData,
        shoppingMapper: ShoppingMapper,
    ) = ShoppingData(shoppingDao, shoppingRemoteData, shoppingMapper)

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
    fun provideShoppingReducer() = ShoppingReducer()
}
