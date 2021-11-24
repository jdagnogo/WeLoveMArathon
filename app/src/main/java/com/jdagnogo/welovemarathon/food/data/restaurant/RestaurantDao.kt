package com.jdagnogo.welovemarathon.food.data.restaurant

import androidx.room.*
import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantEntity.Companion.TABLE_RESTAURANT
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(restaurants: List<RestaurantEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<RestaurantEntity>>

    @Transaction
    suspend fun updateBlogs(restaurants: List<RestaurantEntity>) {
        clear()
        insertAll(restaurants)
    }

    companion object {
        private const val QUERY_DELETE = "DELETE FROM $TABLE_RESTAURANT"
        private const val QUERY_GET_ALL = "SELECT * FROM $TABLE_RESTAURANT"
    }
}
