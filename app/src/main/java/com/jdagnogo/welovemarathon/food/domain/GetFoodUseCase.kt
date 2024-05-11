package com.jdagnogo.welovemarathon.food.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import com.jdagnogo.welovemarathon.food.domain.FoodCategory.Companion.ALL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFoodUseCase @Inject constructor(
    private val repository: FoodRepository,
) {
    operator fun invoke(
        type: String? = null,
        tags: List<String> = emptyList()
    ): Flow<Resource<List<Food>>> {
        return repository.data.map { list ->
            var result =
                list.data?.sortedBy { it.name }?.toMutableList() ?: listOf()
            if (type != null && type != ALL) {
                result = result.filter { it.category.contains(type) }
            }

            if (tags.isNotEmpty()) {
                return@map Resource.Success(result.filter { food ->
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
            return@map Resource.Success(result)
        }
    }
}
