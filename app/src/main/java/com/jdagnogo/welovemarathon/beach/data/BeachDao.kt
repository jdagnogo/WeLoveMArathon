package com.jdagnogo.welovemarathon.beach.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface BeachDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<BeachEntity>)

    @Query(QUERY_DELETE_BEACHES)
    suspend fun clear()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<BeachEntity>>

    @Transaction
    suspend fun update(beaches: List<BeachEntity>) {
        clear()
        insertAll(beaches)
    }

    companion object {
        private const val QUERY_DELETE_BEACHES = "DELETE FROM ${BeachEntity.TABLE_BEACH}"
        private const val QUERY_GET_ALL = "SELECT * FROM ${BeachEntity.TABLE_BEACH}"
    }
}
