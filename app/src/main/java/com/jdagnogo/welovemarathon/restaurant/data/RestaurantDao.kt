package com.jdagnogo.welovemarathon.restaurant.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<RestaurantEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFilter(entities: List<RestaurantFilterEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_DELETE_FILTER)
    suspend fun clearFilter()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<RestaurantEntity>>

    @Query(QUERY_GET_ALL_FILTER)
    fun getAllFilter(): Flow<List<RestaurantFilterEntity>>

    @Transaction
    suspend fun update(foods: List<RestaurantEntity>) {
        clear()
        insertAll(foods)
    }

    @Transaction
    suspend fun updateFilter(filter: List<RestaurantFilterEntity>) {
        clearFilter()
        insertAllFilter(filter)
    }

    companion object {
        private const val QUERY_GET_ALL = "SELECT * FROM ${RestaurantEntity.TABLE}"
        private const val QUERY_GET_ALL_FILTER = "SELECT * FROM ${RestaurantFilterEntity.TABLE}"
        private const val QUERY_DELETE = "DELETE FROM ${RestaurantEntity.TABLE}"
        private const val QUERY_DELETE_FILTER = "DELETE FROM ${RestaurantFilterEntity.TABLE}"
    }
}