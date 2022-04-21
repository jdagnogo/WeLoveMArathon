package com.jdagnogo.welovemarathon.food.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<FoodEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_DELETE_CATEGORY)
    suspend fun clearCategory()

    @Query(QUERY_DELETE_TAG)
    suspend fun clearTags()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<FoodEntity>>

    @Query(QUERY_GET_ALL_SHOPPING_CATEGORY)
    fun getAllCategories(): Flow<List<FoodCategoryEntity>>

    @Query(QUERY_GET_ALL_TAG)
    fun getAllTags(): Flow<List<FoodTagEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(entities: List<FoodCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTags(entities: List<FoodTagEntity>)

    @Transaction
    suspend fun update(foods: List<FoodEntity>) {
        clear()
        insertAll(foods)
    }

    @Transaction
    suspend fun updateCategories(categoryEntities: List<FoodCategoryEntity>) {
        clearCategory()
        insertAllCategories(categoryEntities)
    }

    @Transaction
    suspend fun updateTags(entities: List<FoodTagEntity>) {
        clearTags()
        insertAllTags(entities)
    }

    companion object {
        private const val QUERY_DELETE = "DELETE FROM ${FoodEntity.TABLE}"
        private const val QUERY_DELETE_CATEGORY = "DELETE FROM ${FoodCategoryEntity.TABLE}"
        private const val QUERY_DELETE_TAG = "DELETE FROM ${FoodTagEntity.TABLE}"
        private const val QUERY_GET_ALL = "SELECT * FROM ${FoodEntity.TABLE}"
        private const val QUERY_GET_ALL_TAG = "SELECT * FROM ${FoodTagEntity.TABLE}"
        private const val QUERY_GET_ALL_SHOPPING_CATEGORY =
            "SELECT * FROM ${FoodCategoryEntity.TABLE}"
    }
}
