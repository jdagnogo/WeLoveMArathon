package com.jdagnogo.welovemarathon.culture.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface CultureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<CultureEntity>)

    @Query(QUERY_DELETE_BEACHES)
    suspend fun clear()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<CultureEntity>>

    @Transaction
    suspend fun update(cultures: List<CultureEntity>) {
        clear()
        insertAll(cultures)
    }

    companion object {
        private const val QUERY_DELETE_BEACHES = "DELETE FROM ${CultureEntity.TABLE}"
        private const val QUERY_GET_ALL = "SELECT * FROM ${CultureEntity.TABLE}"
    }
}
