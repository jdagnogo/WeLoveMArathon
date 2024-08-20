package com.jdagnogo.welovemarathon.offer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface OfferDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<OfferEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<OfferEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(entities: List<OfferEntity>)


    @Transaction
    suspend fun update(activities: List<OfferEntity>) {
        clear()
        insertAll(activities)
    }


    companion object {
        private const val QUERY_DELETE = "DELETE FROM ${OfferEntity.TABLE}"
        private const val QUERY_GET_ALL = "SELECT * FROM ${OfferEntity.TABLE}"
    }
}
