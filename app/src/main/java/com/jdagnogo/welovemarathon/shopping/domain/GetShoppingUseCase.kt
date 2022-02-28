package com.jdagnogo.welovemarathon.shopping.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.shopping.data.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShoppingUseCase @Inject constructor(private val repository: ShoppingRepository) {
    suspend operator fun invoke(type: String): Flow<Resource<List<Shopping>>> {
        return repository.data.map { list ->
            val result =
                list.data
                    ?.filter { it.category == type }
                    ?.sortedBy { it.name }?.toMutableList() ?: listOf()
            return@map Resource.Success(result)
        }
    }
}
