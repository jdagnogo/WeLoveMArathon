package com.jdagnogo.welovemarathon.food.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFoodTagUseCase @Inject constructor(private val repository: FoodRepository) {
    operator fun invoke(category: String): Flow<Resource<List<FoodTag>>> {
        return repository.tags.map { list ->
            Resource.Success(list.data?.sortedBy { it.name }?.filter {
                it.category.contains(category, ignoreCase = true)
            } ?: listOf())
        }
    }
}
