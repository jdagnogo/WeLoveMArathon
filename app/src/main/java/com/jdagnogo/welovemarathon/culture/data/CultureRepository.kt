package com.jdagnogo.welovemarathon.culture.data

import com.jdagnogo.welovemarathon.culture.domain.Culture
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

interface CultureRepository {
    val cultures: StateFlow<Resource<List<Culture>>>
}

class CultureRepositoryImpl @Inject constructor(
    private val cultureData: CultureData,
    private val coroutineScope: CoroutineScope,
) : CultureRepository {
    private val _cultures: MutableStateFlow<Resource<List<Culture>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val cultures: StateFlow<Resource<List<Culture>>>
        get() = _cultures

    init {
        fetchCultures()
    }

    private fun fetchCultures(forceFetch: Boolean = false) {
        coroutineScope.launch {
            with(cultureData) {
                val cultures = resourceAsFlow(
                    forceFetch = forceFetch,
                    fetchFromLocal = { cultureDao.getAll().map { cultureMapper.toCultureCategories(it) } },
                    networkCall = { cultureRemoteData.getCultureCategories() },
                    saveCallResource = { cultures ->
                        val cultureEntities = cultureMapper.toCultureEntities(cultures)
                        cultureDao.update(cultureEntities)
                    },
                    checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.CULTURE) })

                cultures.collectLatest {
                    _cultures.value = it
                }
            }
        }
    }
}
