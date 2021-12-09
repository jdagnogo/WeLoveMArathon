package com.jdagnogo.welovemarathon.beach.data

import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.beach.domain.PrivateBeach
import com.jdagnogo.welovemarathon.common.domain.DataType
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface BeachRepository {
    suspend fun getBeaches(forceFetch: Boolean = false): Flow<Resource<List<Beach>>>
    suspend fun getPrivatesBeaches(
        parentId: String,
        forceFetch: Boolean = false,
    ): Flow<Resource<List<PrivateBeach>>>
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
                checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.BEACH) })
        }
    }

    override suspend fun getPrivatesBeaches(
        parentId: String,
        forceFetch: Boolean,
    ): Flow<Resource<List<PrivateBeach>>> {
        with(beachData) {
            return resourceAsFlow(
                forceFetch = forceFetch,
                fetchFromLocal = {
                    beachDao.getPrivateBeaches(parentId).map { beachMapper.toPrivateBeaches(it) }
                },
                networkCall = { beachRemoteData.getPrivateBeaches() },
                saveCallResource = { beaches ->
                    val beachEntities = beachMapper.toPrivateBeachEntities(beaches)
                    beachDao.updatePrivateBeaches(beachEntities)
                },
                checkDataFreshness = { false }
            )
        }
    }
}
