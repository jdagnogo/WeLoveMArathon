package com.jdagnogo.welovemarathon.shopping.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.shopping.data.ShoppingRepository
import com.jdagnogo.welovemarathon.sport.data.SportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShoppingCategoriesUseCase @Inject constructor(private val repository: ShoppingRepository) {
    suspend operator fun invoke(): Flow<Resource<List<ShoppingCategory>>> {
        return repository.categories.map { list ->
            Resource.Success(list.data?.sortedBy { it.ordinal } ?: listOf())
        }
    }
}
