package com.jdagnogo.welovemarathon.beach.data

import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.common.domain.DataType
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

interface BeachRepository {
    val beaches: StateFlow<Resource<List<Beach>>>
}

class BeachRepositoryImpl @Inject constructor(
    private val beachData: BeachData,
    private val coroutineScope: CoroutineScope,
) : BeachRepository {
    private val _beaches: MutableStateFlow<Resource<List<Beach>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val beaches: StateFlow<Resource<List<Beach>>>
        get() = _beaches

    init {
        fetchBeaches()
    }

    private fun fetchBeaches(forceFetch: Boolean = false) {
        coroutineScope.launch {
            with(beachData) {
                val beaches = resourceAsFlow(
                    forceFetch = forceFetch,
                    fetchFromLocal = { beachDao.getAll().map { beachMapper.toBeaches(it) } },
                    networkCall = { beachRemoteData.getBeaches() },
                    saveCallResource = { beaches ->
                        val beachEntities = beachMapper.toBeachEntities(beaches)
                        beachDao.update(beachEntities)
                    },
                    checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.BEACH) })

                beaches.collectLatest {
                    _beaches.value = it
                }
            }
        }
    }
}
