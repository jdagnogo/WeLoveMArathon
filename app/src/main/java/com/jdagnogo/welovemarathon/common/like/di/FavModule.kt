package com.jdagnogo.welovemarathon.common.like.di

import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.common.like.FavMapper
import com.jdagnogo.welovemarathon.common.like.data.FavDao
import com.jdagnogo.welovemarathon.common.like.data.FavData
import com.jdagnogo.welovemarathon.common.like.data.FavRepository
import com.jdagnogo.welovemarathon.common.like.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavModule {

    @Provides
    @Singleton
    fun provideGetFavUseCases(
        repository: FavRepository,
    ): GetAllFavUseCases {
        return GetAllFavUseCases(repository)
    }

    @Provides
    @Singleton
    fun provideRemoveFromFavoriteUseCase(
        repository: FavRepository,
    ): RemoveFromFavoriteUseCase {
        return RemoveFromFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteAllFavoriteUseCase(
        repository: FavRepository,
    ): DeleteAllFavoriteUseCase {
        return DeleteAllFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddToFavoriteUseCase(
        repository: FavRepository,
    ): AddToFavoriteUseCase {
        return AddToFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFavData(
        favDao: FavDao,
        favMapper: FavMapper,
    ) = FavData(favDao, favMapper)

    @Provides
    @Singleton
    fun provideFavMapper(): FavMapper {
        return FavMapper()
    }

    @Singleton
    @Provides
    fun provideFavDao(db: WLMDatabase): FavDao = db.getFavDao()

    @Provides
    @Singleton
    fun provideFavUseCase(
        getAllFavUseCases: GetAllFavUseCases,
        updateFavUseCase: UpdateFavUseCase,
        deleteAllFavoriteUseCase: DeleteAllFavoriteUseCase,
    ) = FavUseCase(
        getAllFavUseCases = getAllFavUseCases,
        updateFavUseCase = updateFavUseCase,
        deleteAllFavoriteUseCase = deleteAllFavoriteUseCase
    )

    @Provides
    @Singleton
    fun provideUpdateFavUseCase(
        addToFavoriteUseCase: AddToFavoriteUseCase,
        removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
        getAllFavUseCases: GetAllFavUseCases,
    ) =
        UpdateFavUseCase(addToFavoriteUseCase, removeFromFavoriteUseCase, getAllFavUseCases)
}
