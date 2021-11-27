package com.jdagnogo.welovemarathon.run.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RunDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(runs: List<RunEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<RunEntity>>

    @Transaction
    suspend fun updateRuns(runs: List<RunEntity>) {
        clear()
        insertAll(runs)
    }

    companion object {
        private const val QUERY_DELETE = "DELETE FROM run_entity"
        private const val QUERY_GET_ALL = "SELECT * FROM run_entity"
    }
}
