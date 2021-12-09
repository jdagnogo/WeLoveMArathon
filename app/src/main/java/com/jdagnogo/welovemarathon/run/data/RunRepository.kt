package com.jdagnogo.welovemarathon.run.data

import com.jdagnogo.welovemarathon.common.domain.DataType
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.run.domain.Run
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface RunRepository {
    suspend fun getRuns(forceFetch: Boolean = false): Flow<Resource<List<Run>>>
}

class RunRepositoryImpl @Inject constructor(
    private val runData: RunData,
) : RunRepository {
    override suspend fun getRuns(forceFetch: Boolean): Flow<Resource<List<Run>>> {
        with(runData) {
            return resourceAsFlow(
                forceFetch = forceFetch,
                fetchFromLocal = { runDao.getAll().map { runMapper.toRuns(it) } },
                networkCall = { runRemoteData.getRuns() },
                saveCallResource = { runs ->
                    val runEntities = runMapper.toRunsEntities(runs)
                    runDao.updateRuns(runEntities)
                },
                checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.RUN) })
        }
    }
}
