package com.jdagnogo.welovemarathon.common.data

import javax.inject.Inject

interface DataFreshnessRepository {
    suspend fun isDataFresh(dataName: String): Long
    suspend fun updateDataFreshness(dataName: String, updateTime: Long)
    suspend fun resetData(dataName: String)
}

class DataFreshnessRepositoryImpl @Inject constructor(val dao: DataFreshnessDao) :
    DataFreshnessRepository {
    override suspend fun isDataFresh(dataName: String): Long {
        return dao.getDataLastUpdate(dataName) ?: 0L
    }

    override suspend fun updateDataFreshness(dataName: String, updateTime: Long) {
        dao.updateDataFreshness(dataName, updateTime)
    }

    override suspend fun resetData(dataName: String) {
        dao.resetData(dataName)
    }
}
