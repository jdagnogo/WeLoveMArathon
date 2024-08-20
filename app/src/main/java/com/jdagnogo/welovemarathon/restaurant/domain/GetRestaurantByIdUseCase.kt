package com.jdagnogo.welovemarathon.restaurant.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantRepository
import com.jdagnogo.welovemarathon.shopping.domain.transformContent
import javax.inject.Inject

class GetRestaurantByIdUseCase @Inject constructor(
    private val repository: RestaurantRepository,
) {
    operator fun invoke(
        id: String?
    ): Resource<Restaurant?> {
        if (id == null) return Resource.Success(null)
        return repository.data.value.transformContent {
            it?.find { it.id == id }
        }
    }
}