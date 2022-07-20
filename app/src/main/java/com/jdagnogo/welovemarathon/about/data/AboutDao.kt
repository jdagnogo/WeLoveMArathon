package com.jdagnogo.welovemarathon.about.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AboutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: AboutEntity)

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<AboutEntity>

    @Transaction
    suspend fun update(entities: AboutEntity) {
        insertAll(entities)
    }

    companion object {
        private const val QUERY_GET_ALL = "SELECT * FROM ${AboutEntity.TABLE}"
    }
}