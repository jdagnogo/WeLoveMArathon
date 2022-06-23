package com.jdagnogo.welovemarathon.common.like.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: FavoriteEntity)

    @Delete
    suspend fun delete(vararg songs: FavoriteEntity)

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<FavoriteEntity>>

    @Query(QUERY_DELETE_ALL)
    suspend fun deleteAll()

    companion object {
        private const val QUERY_GET_ALL = "SELECT * FROM ${FavoriteEntity.TABLE}"
        private const val QUERY_DELETE_ALL = "DELETE FROM ${FavoriteEntity.TABLE}"
    }
}