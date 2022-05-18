package com.jdagnogo.welovemarathon.beach.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBeachesBarUseCase @Inject constructor(
    private val repository: FoodRepository,
) {
    operator fun invoke(parent: String? = null): Flow<Resource<List<BeachBar>>> {
        return repository.data.map { list ->
            var result =
                list.data?.filter {
                    it.isBeachBar
                }

            if (parent != null) {
                result = result?.filter { it.parent == parent }
            }
            return@map Resource.Success(result?.sortedBy { it.name }?.toMutableList()?.map {
                it.toBeachBar()
            } ?: listOf())
        }
    }
}
