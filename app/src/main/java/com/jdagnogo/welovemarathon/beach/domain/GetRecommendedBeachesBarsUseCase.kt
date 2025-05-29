package com.jdagnogo.welovemarathon.beach.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecommendedBeachesBarsUseCase @Inject constructor(
    private val repository: FoodRepository,
) {
    operator fun invoke(): Flow<Resource<List<BeachBar>>> {
        return repository.data.map { list ->
            val result =
                list.data?.filter {
                    it.isBeachBar && it.isRecommended
                }?.sortedBy { it.name }?.toMutableList()?.map {
                    it.toBeachBar()
                } ?: listOf()

            return@map Resource.Success(result)
        }
    }
}
