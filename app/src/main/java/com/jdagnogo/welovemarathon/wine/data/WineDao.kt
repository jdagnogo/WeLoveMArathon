package com.jdagnogo.welovemarathon.wine.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WineDao {
    @Query(QUERY_GET_ALL_TOUR)
    fun getAllWineTour(): Flow<List<WineTourEntity>>

    @Query(QUERY_GET_ALL_SOCIAL)
    fun getAllWineSocial(): Flow<List<WineSocialEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWineTour(blogs: List<WineTourEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWineSocial(blogs: List<WineSocialEntity>)

    @Query(QUERY_DELETE_SOCIAL)
    suspend fun clearWineTour()

    @Query(QUERY_DELETE_TOUR)
    suspend fun clearWineSocial()

    @Transaction
    suspend fun updateWineTour(data: List<WineTourEntity>) {
        clearWineTour()
        insertAllWineTour(data)
    }

    @Transaction
    suspend fun updateWineSocial(data: List<WineSocialEntity>) {
        clearWineSocial()
        insertAllWineSocial(data)
    }

    companion object {
        private const val QUERY_DELETE_SOCIAL = "DELETE FROM ${WineSocialEntity.TABLE}"
        private const val QUERY_DELETE_TOUR = "DELETE FROM ${WineTourEntity.TABLE}"
        private const val QUERY_GET_ALL_TOUR = "SELECT * FROM ${WineTourEntity.TABLE}"
        private const val QUERY_GET_ALL_SOCIAL = "SELECT * FROM ${WineSocialEntity.TABLE}"
    }
}