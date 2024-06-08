package com.jdagnogo.welovemarathon.restaurant.data

import com.jdagnogo.welovemarathon.common.domain.DataType
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

interface RestaurantRepository {
    val data: StateFlow<Resource<List<Restaurant>>>
    val filters: StateFlow<Resource<List<RestaurantFilter>>>
}

class RestaurantRepositoryImpl @Inject constructor(
    private val foodData: RestaurantData,
    private val coroutineScope: CoroutineScope,
) : RestaurantRepository {
    private val _data: MutableStateFlow<Resource<List<Restaurant>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val data: StateFlow<Resource<List<Restaurant>>>
        get() = _data

    private val _filters: MutableStateFlow<Resource<List<RestaurantFilter>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val filters: StateFlow<Resource<List<RestaurantFilter>>>
        get() = _filters

    init {
        fetchData()
        fetchFilter()
    }

    private fun fetchData(forceFetch: Boolean = false) {
        coroutineScope.launch {
            with(foodData) {
                val categories = resourceAsFlow(
                    forceFetch = forceFetch,
                    fetchFromLocal = { dao.getAll().map { mapper.toDomain(it) } },
                    networkCall = { remoteData.get() },
                    saveCallResource = { sports ->
                        val sportEntities = mapper.toEntities(sports)
                        dao.update(sportEntities)
                    },
                    checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.RESTAURANT) })

                categories.collectLatest {
                    _data.value = it
                }
            }
        }
    }

    private fun fetchFilter(forceFetch: Boolean = false) {
        coroutineScope.launch {
            with(foodData) {
                val categories = resourceAsFlow(
                    forceFetch = forceFetch,
                    fetchFromLocal = { dao.getAllFilter().map { mapper.toDomainFilter(it) } },
                    networkCall = { remoteData.getFilter() },
                    saveCallResource = { sports ->
                        val sportEntities = mapper.toEntitiesFilter(sports)
                        dao.updateFilter(sportEntities)
                    },
                    checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.RESTAURANT_FILTER) })

                categories.collectLatest {
                    _filters.value = it
                }
            }
        }
    }
}