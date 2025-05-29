package com.jdagnogo.welovemarathon.tips.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TipsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(blogs: List<TipsEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<TipsEntity>>

    @Transaction
    suspend fun update(Tips: List<TipsEntity>) {
        clear()
        insertAll(Tips)
    }

    companion object {
        private const val QUERY_DELETE = "DELETE FROM ${TipsEntity.TABLE}"
        private const val QUERY_GET_ALL = "SELECT * FROM ${TipsEntity.TABLE}"
    }
}
