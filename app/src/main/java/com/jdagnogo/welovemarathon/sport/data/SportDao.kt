package com.jdagnogo.welovemarathon.sport.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SportDao {
    @Query(QUERY_DELETE_SPORT)
    suspend fun clear()

    @Query(QUERY_DELETE_SPORT_CATEGORY)
    suspend fun clearCategory()

    @Query(QUERY_GET_ALL_SPORTS)
    fun getAll(): Flow<List<SportEntity>>

    @Query(QUERY_GET_ALL_SPORTS_CATEGORY)
    fun getAllCategories(): Flow<List<SportCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<SportEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(foods: List<SportCategoryEntity>)

    @Transaction
    suspend fun updateCategories(categoryEntities: List<SportCategoryEntity>) {
        clearCategory()
        insertAllCategories(categoryEntities)
    }

    @Transaction
    suspend fun update(sportEntityEntities: List<SportEntity>) {
        clear()
        insertAll(sportEntityEntities)
    }

    companion object {
        private const val QUERY_DELETE_SPORT = "DELETE FROM ${SportEntity.TABLE}"
        private const val QUERY_DELETE_SPORT_CATEGORY = "DELETE FROM ${SportCategoryEntity.TABLE}"
        private const val QUERY_GET_ALL_SPORTS = "SELECT * FROM ${SportEntity.TABLE}"
        private const val QUERY_GET_ALL_SPORTS_CATEGORY =
            "SELECT * FROM ${SportCategoryEntity.TABLE}"
    }
}
