package com.jdagnogo.welovemarathon.common.like.domain

import com.jdagnogo.welovemarathon.common.like.data.FavRepository
import javax.inject.Inject

class DeleteAllFavoriteUseCase @Inject constructor(
    private val repository: FavRepository,
) {
    suspend operator fun invoke() {
        repository.deleteAllFavorite()
    }
}