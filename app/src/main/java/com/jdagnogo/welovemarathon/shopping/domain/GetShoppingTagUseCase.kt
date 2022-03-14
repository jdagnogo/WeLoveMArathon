package com.jdagnogo.welovemarathon.shopping.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.shopping.data.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShoppingTagUseCase @Inject constructor(private val repository: ShoppingRepository) {
    suspend operator fun invoke(): Flow<Resource<List<ShoppingTag>>> {
        return repository.tags.map { list ->
            Resource.Success(list.data?.sortedBy { it.name } ?: listOf())
        }
    }
}
