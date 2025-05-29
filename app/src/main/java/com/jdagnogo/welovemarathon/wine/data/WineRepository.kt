package com.jdagnogo.welovemarathon.wine.data

import com.jdagnogo.welovemarathon.common.domain.DataType
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.shopping.domain.transformContent
import com.jdagnogo.welovemarathon.wine.domain.WineSocial
import com.jdagnogo.welovemarathon.wine.domain.WineTour
import com.jdagnogo.welovemarathon.wine.domain.WineryInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

interface WineRepository {
    val tour: StateFlow<Resource<List<WineTour>>>
    val social: StateFlow<Resource<List<WineSocial>>>
    val info: StateFlow<Resource<List<WineryInfo>>>
}

class WineRepositoryImpl @Inject constructor(
    private val wineData: WineData,
    private val coroutineScope: CoroutineScope,
) : WineRepository {
    private val _tour: MutableStateFlow<Resource<List<WineTour>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val tour: StateFlow<Resource<List<WineTour>>>
        get() = _tour

    private val _social: MutableStateFlow<Resource<List<WineSocial>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val social: StateFlow<Resource<List<WineSocial>>>
        get() = _social

    private val _info: MutableStateFlow<Resource<List<WineryInfo>>> =
        MutableStateFlow(Resource.Loading(emptyList()))
    override val info: StateFlow<Resource<List<WineryInfo>>>
        get() = _info

    init {
        fetchWineInfo()
        fetchWineTour()
        fetchWineSocial()
    }

    private fun fetchWineInfo(forceFetch: Boolean = false) {
        coroutineScope.launch {
            with(wineData) {
                val info = resourceAsFlow(
                    forceFetch = forceFetch,
                    fetchFromLocal = {
                        dao.getWineInfo().map { it.map { info -> info.toWineryInfo() } }
                    },
                    networkCall = {
                        remoteData.getWineInfo()
                    },
                    saveCallResource = { infos ->
                        val entities = infos.map { it.toWineryInfoEntity() }
                        dao.updateWineInfo(entities)
                    },
                    checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.WINE_INFO) })

                info.collectLatest { _info.value = it }
            }
        }
    }

    private fun fetchWineTour(forceFetch: Boolean = false) {
        coroutineScope.launch {
            with(wineData) {
                val categories = resourceAsFlow(
                    forceFetch = forceFetch,
                    fetchFromLocal = {
                        dao.getAllWineTour().map { mapper.toWineTour(it) }
                    },
                    networkCall = {
                        remoteData.getWineTour()
                    },
                    saveCallResource = { categories ->
                        val entities = mapper.toWineTourEntities(categories)
                        dao.updateWineTour(entities)
                    },
                    checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.WINE_TOUR) })

                categories.collectLatest {
                    _tour.value = it
                }
            }
        }
    }

    private fun fetchWineSocial(forceFetch: Boolean = false) {
        coroutineScope.launch {
            with(wineData) {
                val categories = resourceAsFlow(
                    forceFetch = forceFetch,
                    fetchFromLocal = {
                        dao.getAllWineSocial().map { mapper.toWineSocial(it) }
                    },
                    networkCall = {
                        remoteData.getWineSocial()
                    },
                    saveCallResource = { categories ->
                        val entities = mapper.toWineSocialEntities(categories)
                        dao.updateWineSocial(entities)
                    },
                    checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.WINE_SOCIAL) })

                categories.collectLatest {
                    _social.value = it
                }
            }
        }
    }
}