package com.jdagnogo.welovemarathon.food.domain.restaurant

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.food.data.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRestaurantUseCase @Inject constructor(private val repository: FoodRepository) {
    suspend operator fun invoke(): Flow<Resource<List<Restaurant>>> {
        return repository.getRestaurants()
    }
}
