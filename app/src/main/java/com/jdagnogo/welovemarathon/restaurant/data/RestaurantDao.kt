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

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<RestaurantEntity>>

    @Transaction
    suspend fun update(foods: List<RestaurantEntity>) {
        clear()
        insertAll(foods)
    }

    companion object {
        private const val QUERY_GET_ALL = "SELECT * FROM ${RestaurantEntity.TABLE}"
        private const val QUERY_DELETE = "DELETE FROM ${RestaurantEntity.TABLE}"
    }
}