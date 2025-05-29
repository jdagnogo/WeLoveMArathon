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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllOfferActivatedEntity(entities: List<OfferActivatedEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_DELETE_OFFER_ACTIVATED)
    suspend fun clearOfferActivatedEntity()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<OfferEntity>>

    @Query(QUERY_GET_ALL_OFFER_ACTIVATED)
    fun getAllOfferActivatedEntity(): Flow<List<OfferActivatedEntity>>



    @Transaction
    suspend fun update(activities: List<OfferEntity>) {
        clear()
        insertAll(activities)
    }

    @Transaction
    suspend fun updateOfferActivatedEntity(activities: List<OfferActivatedEntity>) {
        clearOfferActivatedEntity()
        insertAllOfferActivatedEntity(activities)
    }


    companion object {
        private const val QUERY_DELETE = "DELETE FROM ${OfferEntity.TABLE}"
        private const val QUERY_GET_ALL = "SELECT * FROM ${OfferEntity.TABLE}"

        private const val QUERY_DELETE_OFFER_ACTIVATED = "DELETE FROM ${OfferActivatedEntity.TABLE}"
        private const val QUERY_GET_ALL_OFFER_ACTIVATED =
            "SELECT * FROM ${OfferActivatedEntity.TABLE}"
    }
}
