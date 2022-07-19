package com.jdagnogo.welovemarathon.about.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AboutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<AboutEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<AboutEntity>>

    @Transaction
    suspend fun update(entities: List<AboutEntity>) {
        clear()
        insertAll(entities)
    }

    companion object {
        private const val QUERY_DELETE = "DELETE FROM ${AboutEntity.TABLE}"
        private const val QUERY_GET_ALL = "SELECT * FROM ${AboutEntity.TABLE}"
    }
}