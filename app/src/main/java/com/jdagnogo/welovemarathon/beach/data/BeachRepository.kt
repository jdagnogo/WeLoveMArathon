package com.jdagnogo.welovemarathon.beach.data

import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface BeachRepository {
    suspend fun getBeaches(forceFetch: Boolean = false): Flow<Resource<List<Beach>>>
}

class BeachRepositoryImpl @Inject constructor(private val beachData: BeachData) : BeachRepository {
    override suspend fun getBeaches(forceFetch: Boolean): Flow<Resource<List<Beach>>> {
        with(beachData) {
            return resourceAsFlow(
                forceFetch = forceFetch,
                fetchFromLocal = { beachDao.getAll().map { beachMapper.toBeaches(it) } },
                networkCall = { beachRemoteData.getBeaches() },
                saveCallResource = { beaches ->
                    val beachEntities = beachMapper.toBeachEntities(beaches)
                    beachDao.update(beachEntities)
                },
                checkDataFreshness = { false }
            )
        }
    }
}
