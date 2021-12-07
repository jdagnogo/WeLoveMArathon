package com.jdagnogo.welovemarathon.food.data.restaurant

import androidx.room.*
import com.jdagnogo.welovemarathon.food.data.restaurant.FoodEntity.Companion.TABLE_RESTAURANT
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<FoodEntity>)

    @Query("DELETE FROM $TABLE_RESTAURANT WHERE type = :type")
    suspend fun clear(type: String)

    @Query("SELECT * FROM $TABLE_RESTAURANT WHERE type = :type")
    fun getFoods(type: String): Flow<List<FoodEntity>>

    @Transaction
    suspend fun update(type: String, foods: List<FoodEntity>) {
        clear(type)
        insertAll(foods)
    }
}
