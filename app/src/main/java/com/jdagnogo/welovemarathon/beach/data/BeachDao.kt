package com.jdagnogo.welovemarathon.beach.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BeachDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<BeachEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPrivateBeaches(data: List<PrivateBeachEntity>)

    @Query(QUERY_DELETE_BEACHES)
    suspend fun clear()

    @Query(QUERY_DELETE_PRIVATE_BEACHES)
    suspend fun clearPrivateBeaches()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<BeachEntity>>

    @Query("SELECT * FROM ${PrivateBeachEntity.TABLE}")
    fun getPrivateBeaches(): Flow<List<PrivateBeachEntity>>

    @Transaction
    suspend fun update(beaches: List<BeachEntity>) {
        clear()
        insertAll(beaches)
    }

    @Transaction
    suspend fun updatePrivateBeaches(beaches: List<PrivateBeachEntity>) {
        clearPrivateBeaches()
        insertAllPrivateBeaches(beaches)
    }

    companion object {
        private const val QUERY_DELETE_BEACHES = "DELETE FROM ${BeachEntity.TABLE_BEACH}"
        private const val QUERY_DELETE_PRIVATE_BEACHES = "DELETE FROM ${PrivateBeachEntity.TABLE}"
        private const val QUERY_GET_ALL = "SELECT * FROM ${BeachEntity.TABLE_BEACH}"
    }
}
