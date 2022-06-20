package com.jdagnogo.welovemarathon.common.like.domain

import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.like.data.FavRepository
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(
    private val repository: FavRepository,
) {
    suspend operator fun invoke(categoryItem: CategoryItem) {
        repository.addToFavorite(categoryItem.toFavorite())
    }
}