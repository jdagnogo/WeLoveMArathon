package com.jdagnogo.welovemarathon.common.like.domain

import com.jdagnogo.welovemarathon.common.category.CategoryItem
import javax.inject.Inject

class UpdateFavUseCase @Inject constructor(
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val getAllFavUseCases: GetAllFavUseCases,
) {
    suspend operator fun invoke(categoryItem: CategoryItem?) {
        if (categoryItem == null) return
        val fav = getAllFavUseCases.invoke().value.firstOrNull { it.id == categoryItem.id }
        if (fav == null) {
            addToFavoriteUseCase(categoryItem)
        } else {
            removeFromFavoriteUseCase(categoryItem)
        }
    }
}