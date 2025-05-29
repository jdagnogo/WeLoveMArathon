package com.jdagnogo.welovemarathon.food.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import com.jdagnogo.welovemarathon.sport.data.SportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFoodCategoriesUseCase @Inject constructor(private val repository: FoodRepository) {
    operator fun invoke(): Flow<Resource<List<FoodCategory>>> {
        return repository.categories.map { list ->
            Resource.Success(list.data?.sortedBy { it.ordinal } ?: listOf())
        }
    }
}
