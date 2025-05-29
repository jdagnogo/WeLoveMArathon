package com.jdagnogo.welovemarathon.offer.di

import com.jdagnogo.welovemarathon.offer.data.OfferFirebaseData
import com.jdagnogo.welovemarathon.offer.data.OfferRemoteData
import com.jdagnogo.welovemarathon.offer.data.OfferRepository
import com.jdagnogo.welovemarathon.offer.data.OfferRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class OfferModuleData {
    @Binds
    abstract fun bindOfferRepository(
        impl: OfferRepositoryImpl,
    ): OfferRepository

    @Binds
    abstract fun bindBlogFirebaseData(
        firebaseData: OfferFirebaseData,
    ): OfferRemoteData
}
