package com.jdagnogo.welovemarathon.shopping.data

import com.jdagnogo.welovemarathon.common.domain.DataType
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategory
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingTag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ShoppingRepository {
    val data: StateFlow<Resource<List<Shopping>>>
    val categories: StateFlow<Resource<List<ShoppingCategory>>>
    val tags: StateFlow<Resource<List<ShoppingTag>>>
}

class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingData: ShoppingData,
    private val coroutineScope: CoroutineScope,
) : ShoppingRepository {
    private val _data: MutableStateFlow<Resource<List<Shopping>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val data: StateFlow<Resource<List<Shopping>>>
        get() = _data

    private val _categories: MutableStateFlow<Resource<List<ShoppingCategory>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val categories: StateFlow<Resource<List<ShoppingCategory>>>
        get() = _categories

    private val _tags: MutableStateFlow<Resource<List<ShoppingTag>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val tags: StateFlow<Resource<List<ShoppingTag>>>
        get() = _tags

    init {
        fetchCategories()
        fetchData()
        fetchTags()
    }

    private fun fetchCategories(forceFetch: Boolean = false) {
        coroutineScope.launch {
            with(shoppingData) {
                val categories = resourceAsFlow(
                    forceFetch = forceFetch,
                    fetchFromLocal = {
                        dao.getAllCategories().map { mapper.toDomainCategories(it) }
                    },
                    networkCall = {
                        remoteData.getCategories()
                    },
                    saveCallResource = { categories ->
                        val tipsEntities = mapper.toEntitiesCategories(categories)
                        dao.updateCategories(tipsEntities)
                    },
                    checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.SHOPPING_CATEGORIES) })

                categories.collectLatest {
                    _categories.value = it
                }
            }
        }
    }

    private fun fetchTags(forceFetch: Boolean = false) {
        coroutineScope.launch {
            with(shoppingData) {
                val categories = resourceAsFlow(
                    forceFetch = forceFetch,
                    fetchFromLocal = {
                        dao.getAllTags().map { mapper.toDomainTag(it) }
                    },
                    networkCall = {
                        remoteData.getTags()
                    },
                    saveCallResource = { categories ->
                        val tipsEntities = mapper.toEntitiesTag(categories)
                        dao.updateTags(tipsEntities)
                    },
                    checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.SHOPPING_TAG) })

                categories.collectLatest {
                    _tags.value = it
                }
            }
        }
    }

    private fun fetchData(forceFetch: Boolean = false) {
        coroutineScope.launch {
            with(shoppingData) {
                val categories = resourceAsFlow(
                    forceFetch = forceFetch,
                    fetchFromLocal = { dao.getAll().map { mapper.toDomain(it) } },
                    networkCall = { remoteData.get() },
                    saveCallResource = { sports ->
                        val sportEntities = mapper.toEntities(sports)
                        dao.update(sportEntities)
                    },
                    checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.SHOPPING) })

                categories.collectLatest {
                    _data.value = it
                }
            }
        }
    }
}
