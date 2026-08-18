package com.jdagnogo.welovemarathon.events.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<EventEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<EventEntity>>

    @Query(QUERY_DELETE_EXPIRED)
    suspend fun deleteExpired(cutoffMillis: Long)

    @Transaction
    suspend fun replaceAll(events: List<EventEntity>) {
        clear()
        insertAll(events)
    }

    companion object {
        private const val QUERY_DELETE = "DELETE FROM ${EventEntity.TABLE}"
        private const val QUERY_GET_ALL = "SELECT * FROM ${EventEntity.TABLE}"
        private const val QUERY_DELETE_EXPIRED =
            "DELETE FROM ${EventEntity.TABLE} WHERE endAt < :cutoffMillis"
    }
}
