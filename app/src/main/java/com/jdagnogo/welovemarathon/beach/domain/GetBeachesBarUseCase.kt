package com.jdagnogo.welovemarathon.beach.domain

import com.jdagnogo.welovemarathon.beach.data.BeachRepository
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBeachesBarUseCase @Inject constructor(
    private val repository: FoodRepository,
) {
    operator fun invoke(parent : String): Flow<Resource<List<BeachBar>>> {
        return repository.data.map { list ->
            val result =
                list.data?.filter {
                    it.isBeachBar && it.parent == parent
                }?.sortedBy { it.name }?.toMutableList()?.map {
                    it.toBeachBar()
                } ?: listOf()

            return@map Resource.Success(result)
        }
    }
}
