package com.jdagnogo.welovemarathon.common.domain

import com.jdagnogo.welovemarathon.common.data.DataFreshnessRepository
import javax.inject.Inject

class DataFreshnessUseCase @Inject constructor(
    private val repository: DataFreshnessRepository,
) {
    suspend fun isDataFresh(dataType: DataType): Boolean {
        return repository.isDataFresh(dataType.name) > System.currentTimeMillis()
    }

    suspend fun updateDataFreshness(dataType: DataType) {
        repository.updateDataFreshness(dataType.name, dataType.generateNextUpdate())
    }
}
