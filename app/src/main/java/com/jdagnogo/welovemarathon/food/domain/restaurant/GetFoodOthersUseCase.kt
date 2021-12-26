package com.jdagnogo.welovemarathon.food.domain.restaurant

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFoodOthersUseCase @Inject constructor(private val repository: FoodRepository) {
    suspend operator fun invoke(type: String): Flow<Resource<List<Food>>> {
        return repository.data.map { list ->
            val result =
                list.data?.filter { it.isRecommended.not() && it.type == type }
                    ?.sortedBy { it.name }?.toMutableList()
                    ?: listOf()
            return@map Resource.Success(result)
        }
    }
}
