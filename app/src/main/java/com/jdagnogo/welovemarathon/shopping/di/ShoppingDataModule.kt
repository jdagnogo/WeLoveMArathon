package com.jdagnogo.welovemarathon.shopping.di

import com.jdagnogo.welovemarathon.shopping.data.ShoppingFirebaseData
import com.jdagnogo.welovemarathon.shopping.data.ShoppingRemoteData
import com.jdagnogo.welovemarathon.shopping.data.ShoppingRepository
import com.jdagnogo.welovemarathon.shopping.data.ShoppingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ShoppingDataModule {
    @Binds
    abstract fun bindShoppingRepository(
        repositoryImpl: ShoppingRepositoryImpl,
    ): ShoppingRepository

    @Binds
    abstract fun bindShoppingFirebaseData(
        firebaseData: ShoppingFirebaseData,
    ): ShoppingRemoteData
}
