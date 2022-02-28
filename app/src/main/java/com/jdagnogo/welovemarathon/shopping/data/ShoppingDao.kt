package com.jdagnogo.welovemarathon.shopping.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shoppings: List<ShoppingEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_DELETE_CATEGORY)
    suspend fun clearCategory()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<ShoppingEntity>>

    @Query(QUERY_GET_ALL_SHOPPING_CATEGORY)
    fun getAllCategories(): Flow<List<ShoppingCategoryEntity>>

    @Query("SELECT * FROM ${ShoppingEntity.TABLE} WHERE category = :shoppingCategory")
    fun getAll(shoppingCategory: ShoppingCategories): Flow<List<ShoppingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(entities: List<ShoppingCategoryEntity>)

    @Transaction
    suspend fun update(shoppings: List<ShoppingEntity>) {
        clear()
        insertAll(shoppings)
    }

    @Transaction
    suspend fun updateCategories(categoryEntities: List<ShoppingCategoryEntity>) {
        clearCategory()
        insertAllCategories(categoryEntities)
    }

    companion object {
        private const val QUERY_DELETE = "DELETE FROM ${ShoppingEntity.TABLE}"
        private const val QUERY_DELETE_CATEGORY = "DELETE FROM ${ShoppingCategoryEntity.TABLE}"
        private const val QUERY_GET_ALL = "SELECT * FROM ${ShoppingEntity.TABLE}"
        private const val QUERY_GET_ALL_SHOPPING_CATEGORY =
            "SELECT * FROM ${ShoppingCategoryEntity.TABLE}"
    }
}
