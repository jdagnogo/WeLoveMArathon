package com.jdagnogo.welovemarathon.shopping.data

import androidx.room.*
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shoppings: List<ShoppingEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<ShoppingEntity>>

    @Query("SELECT * FROM ${ShoppingEntity.TABLE} WHERE category = :shoppingCategory")
    fun getAll(shoppingCategory: ShoppingCategories): Flow<List<ShoppingEntity>>

    @Transaction
    suspend fun update(shoppings: List<ShoppingEntity>) {
        clear()
        insertAll(shoppings)
    }

    companion object {
        private const val QUERY_DELETE = "DELETE FROM ${ShoppingEntity.TABLE}"
        private const val QUERY_GET_ALL = "SELECT * FROM ${ShoppingEntity.TABLE}"
    }
}
