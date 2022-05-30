package com.jdagnogo.welovemarathon.beach.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBeachesBarUseCase @Inject constructor(
    private val repository: FoodRepository,
) {
    operator fun invoke(
        parent: String? = null,
        tags: List<String> = emptyList()
    ): Flow<Resource<List<BeachBar>>> {
        return repository.data.map { list ->
            var result =
                list.data?.filter {
                    it.isBeachBar
                }
            if (parent != null) {
                result = result?.filter { it.parent == parent }
            }
            val beaches = result?.sortedBy { it.name }?.toMutableList()?.map {
                it.toBeachBar()
            } ?: listOf()
            if (tags.isNotEmpty()) {
                return@map Resource.Success(beaches.filter { food ->
                    var containsTags = false
                    tags.forEach { tag ->
                        if (food.tags.contains(tag, ignoreCase = true)) {
                            containsTags = true
                            return@forEach
                        }
                    }
                    containsTags
                })
            }
            return@map Resource.Success(beaches)
        }
    }
}
