package com.jdagnogo.welovemarathon.common.data

import androidx.room.*

@Dao
interface DataFreshnessDao {
    @Query("SELECT lastUpdate FROM dataFreshness WHERE dataName LIKE :dataName")
    suspend fun getDataLastUpdate(dataName: String): Long?

    @Transaction
    suspend fun updateDataFreshness(dataName: String, updateTime: Long) {
        val data = getDataFreshness(dataName) ?: DataFreshnessEntity(
            dataName = dataName,
            lastUpdate = updateTime
        )
        data.lastUpdate = updateTime
        insert(data)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dataFreshnessRoom: DataFreshnessEntity)

    @Query("SELECT * FROM dataFreshness WHERE  dataName LIKE :dataName")
    suspend fun getDataFreshness(dataName: String): DataFreshnessEntity?

    @Transaction
    suspend fun resetData(dataName: String) {
        getDataFreshness(dataName)?.let {
            it.lastUpdate = 0
            insert(it)
        }
    }
}
