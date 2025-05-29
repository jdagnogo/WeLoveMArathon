package com.jdagnogo.welovemarathon.common.like.domain

import com.jdagnogo.welovemarathon.common.like.data.FavRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAllFavUseCases @Inject constructor(
    private val repository: FavRepository,
) {
    operator fun invoke(): StateFlow<List<Favorite>> {
        return repository.favorite
    }
}