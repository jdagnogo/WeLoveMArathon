package com.jdagnogo.welovemarathon.activities.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivitiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<ActivitiesEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_DELETE_CATEGORY)
    suspend fun clearCategory()

    @Query(QUERY_DELETE_TAG)
    suspend fun clearTags()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<ActivitiesEntity>>

    @Query(QUERY_GET_ALL_SHOPPING_CATEGORY)
    fun getAllCategories(): Flow<List<ActivitiesCategoryEntity>>

    @Query(QUERY_GET_ALL_TAG)
    fun getAllTags(): Flow<List<ActivitiesTagEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(entities: List<ActivitiesCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTags(entities: List<ActivitiesTagEntity>)

    @Transaction
    suspend fun update(activities: List<ActivitiesEntity>) {
        clear()
        insertAll(activities)
    }

    @Transaction
    suspend fun updateCategories(categoryEntities: List<ActivitiesCategoryEntity>) {
        clearCategory()
        insertAllCategories(categoryEntities)
    }

    @Transaction
    suspend fun updateTags(entities: List<ActivitiesTagEntity>) {
        clearTags()
        insertAllTags(entities)
    }

    companion object {
        private const val QUERY_DELETE = "DELETE FROM ${ActivitiesEntity.TABLE}"
        private const val QUERY_DELETE_CATEGORY = "DELETE FROM ${ActivitiesCategoryEntity.TABLE}"
        private const val QUERY_DELETE_TAG = "DELETE FROM ${ActivitiesTagEntity.TABLE}"
        private const val QUERY_GET_ALL = "SELECT * FROM ${ActivitiesEntity.TABLE}"
        private const val QUERY_GET_ALL_TAG = "SELECT * FROM ${ActivitiesTagEntity.TABLE}"
        private const val QUERY_GET_ALL_SHOPPING_CATEGORY =
            "SELECT * FROM ${ActivitiesCategoryEntity.TABLE}"
    }
}
