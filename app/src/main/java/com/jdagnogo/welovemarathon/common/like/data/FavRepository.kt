package com.jdagnogo.welovemarathon.common.like.data

import androidx.room.Query
import com.jdagnogo.welovemarathon.common.like.domain.Favorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

interface FavRepository {
    val favorite: StateFlow<List<Favorite>>
    suspend fun addToFavorite(favorite: Favorite)
    suspend fun deleteAllFavorite()
    suspend fun removeFromFavorite(favorite: Favorite)
}

class FavRepositoryImpl @Inject constructor(
    private val favData: FavData,
    private val coroutineScope: CoroutineScope,
) : FavRepository {
    private val _favorite: MutableStateFlow<List<Favorite>> =
        MutableStateFlow(listOf())
    override val favorite: StateFlow<List<Favorite>>
        get() = _favorite


    override suspend fun addToFavorite(favorite: Favorite) {
        favData.dao.insert(favData.mapper.toEntity(favorite))
    }

    override suspend fun deleteAllFavorite() {
        favData.dao.deleteAll()
    }

    override suspend fun removeFromFavorite(favorite: Favorite) {
        favData.dao.delete(favData.mapper.toEntity(favorite))
    }

    init {
        fetchFav()
    }

    private fun fetchFav() {
        coroutineScope.launch {
            favData.dao.getAll().collectLatest {
                _favorite.value = favData.mapper.toDomain(it)
            }
        }
    }
}