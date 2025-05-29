package com.jdagnogo.welovemarathon.offer.di

import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import com.jdagnogo.welovemarathon.offer.data.OfferDao
import com.jdagnogo.welovemarathon.offer.data.OfferData
import com.jdagnogo.welovemarathon.offer.data.OfferMapper
import com.jdagnogo.welovemarathon.offer.data.OfferRemoteData
import com.jdagnogo.welovemarathon.offer.data.OfferRepository
import com.jdagnogo.welovemarathon.offer.domain.ActivateOfferUseCase
import com.jdagnogo.welovemarathon.offer.domain.GetOfferActivatedUseCase
import com.jdagnogo.welovemarathon.offer.domain.GetOfferCountUseCase
import com.jdagnogo.welovemarathon.offer.domain.GetOfferUseCase
import com.jdagnogo.welovemarathon.offer.domain.IsOfferAvailableUseCase
import com.jdagnogo.welovemarathon.offer.domain.OfferUseCase
import com.jdagnogo.welovemarathon.offer.domain.UpdateOfferDisplayCountUseCase
import com.jdagnogo.welovemarathon.offer.presentation.OfferReducer
import com.jdagnogo.welovemarathon.restaurant.domain.GetRestaurantByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OfferModule {

    @Provides
    @Singleton
    fun provideGetOfferUseCase(
        repository: OfferRepository,
        getRestaurantByIdUseCase: GetRestaurantByIdUseCase,
        isOfferAvailableUseCase: IsOfferAvailableUseCase
    ): GetOfferUseCase {
        return GetOfferUseCase(repository, getRestaurantByIdUseCase, isOfferAvailableUseCase)
    }

    @Provides
    @Singleton
    fun provideActivateOfferUseCase(
        repository: OfferRepository,
    ): ActivateOfferUseCase {
        return ActivateOfferUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetOfferActivatedUseCase(
        repository: OfferRepository,
    ): GetOfferActivatedUseCase {
        return GetOfferActivatedUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideUpdateOfferDisplayCountUseCase(
        repository: OfferRepository,
    ): UpdateOfferDisplayCountUseCase {
        return UpdateOfferDisplayCountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetOfferCountUseCase(
        repository: OfferRepository,
    ): GetOfferCountUseCase {
        return GetOfferCountUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideIsOfferAvailableUseCase(
        repository: OfferRepository,
    ): IsOfferAvailableUseCase {
        return IsOfferAvailableUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideOfferData(
        foodDao: OfferDao,
        foodRemoteData: OfferRemoteData,
        foodMapper: OfferMapper,
        dataFreshnessUseCase: DataFreshnessUseCase,
    ) = OfferData(foodDao, foodRemoteData, dataFreshnessUseCase, foodMapper)

    @Provides
    @Singleton
    fun provideOfferMapper(): OfferMapper {
        return OfferMapper()
    }

    @Singleton
    @Provides
    fun provideOfferDao(db: WLMDatabase): OfferDao = db.getOfferDao()

    @Provides
    @Singleton
    fun provideOfferUseCase(
        getOfferUseCase: GetOfferUseCase,
        getOfferActivatedUseCase: GetOfferActivatedUseCase,
        activateOfferUseCase: ActivateOfferUseCase,
    ) = OfferUseCase(
        getOfferUseCase = getOfferUseCase,
        getOfferActivatedUseCase = getOfferActivatedUseCase,
        activateOfferUseCase = activateOfferUseCase,
    )

    @Provides
    @Singleton
    fun provideOfferReducer() = OfferReducer()

}
