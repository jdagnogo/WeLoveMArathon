package com.jdagnogo.welovemarathon.shopping.data

import com.jdagnogo.welovemarathon.common.domain.DataType
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ShoppingRepository {
    suspend fun getShoppings(
        forceFetch: Boolean = false,
    ): Flow<Resource<List<Shopping>>>
}

class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingData: ShoppingData,
) : ShoppingRepository {
    override suspend fun getShoppings(
        forceFetch: Boolean,
    ): Flow<Resource<List<Shopping>>> {
        with(shoppingData) {
            return resourceAsFlow(
                forceFetch = forceFetch,
                fetchFromLocal = {
                    shoppingDao.getAll()
                        .map { shoppingMapper.toShoppings(it) }
                },
                networkCall = { shoppingRemoteData.getShoppings() },
                saveCallResource = { shoppings ->
                    val shoppingEntities = shoppingMapper.toShoppingsEntities(shoppings)
                    shoppingDao.update(shoppingEntities)
                },
                checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.SHOPPING) })
        }
    }
}
