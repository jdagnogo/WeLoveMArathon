package com.jdagnogo.welovemarathon.sport.data

import com.jdagnogo.welovemarathon.common.domain.DataType
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.sport.domain.Sport
import com.jdagnogo.welovemarathon.sport.domain.SportCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SportRepository {
    val data: StateFlow<Resource<List<Sport>>>
    val categories: StateFlow<Resource<List<SportCategory>>>
}

class SportRepositoryIml @Inject constructor(
    private val sportData: SportData,
    private val coroutineScope: CoroutineScope,
) : SportRepository {

    private val _data: MutableStateFlow<Resource<List<Sport>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val data: StateFlow<Resource<List<Sport>>>
        get() = _data

    private val _categories: MutableStateFlow<Resource<List<SportCategory>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val categories: StateFlow<Resource<List<SportCategory>>>
        get() = _categories

    init {
        coroutineScope.launch {
            fetchCategories()
            fetchSports()
        }
    }

    private suspend fun fetchCategories(forceFetch: Boolean = false) {
        with(sportData) {
            val categories = resourceAsFlow(
                forceFetch = forceFetch,
                fetchFromLocal = { dao.getAllCategories().map { mapper.toSportCategories(it) } },
                networkCall = { remoteData.getSportCategories() },
                saveCallResource = { categories ->
                    val tipsEntities = mapper.toSportCategoriesEntities(categories)
                    dao.updateCategories(tipsEntities)
                },
                checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.SPORT) })

            categories.collectLatest {
                _categories.value = it
            }
        }
    }

    private suspend fun fetchSports(forceFetch: Boolean = false) {
        with(sportData) {
            val categories = resourceAsFlow(
                forceFetch = forceFetch,
                fetchFromLocal = { dao.getAll().map { mapper.toSports(it) } },
                networkCall = { remoteData.getSports() },
                saveCallResource = { sports ->
                    val sportEntities = mapper.toSportsEntities(sports)
                    dao.update(sportEntities)
                },
                checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.TIPS) })

            categories.collectLatest {
                _data.value = it
            }
        }
    }
}
