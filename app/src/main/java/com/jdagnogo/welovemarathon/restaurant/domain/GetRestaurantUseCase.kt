package com.jdagnogo.welovemarathon.restaurant.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRestaurantUseCase @Inject constructor(
    private val repository: RestaurantRepository,
) {
    operator fun invoke(
        filter: RestaurantFilter
    ): Flow<Resource<List<Restaurant>>> {
        return repository.data
    }
}