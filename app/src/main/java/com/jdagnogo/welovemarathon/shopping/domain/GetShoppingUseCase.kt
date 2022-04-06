package com.jdagnogo.welovemarathon.shopping.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.shopping.data.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShoppingUseCase @Inject constructor(
    private val repository: ShoppingRepository,
) {
    suspend operator fun invoke(type: String? = null, tags: List<String> = emptyList()): Flow<Resource<List<Shopping>>> {
        return repository.data.map { list ->
            var result =
                list.data?.sortedBy { it.name }?.toMutableList() ?: listOf()
            if (type != null) {
                result = result.filter { it.category.contains(type) }
            }

            if (tags.isNotEmpty()) {
                return@map Resource.Success(result.filter { shopping ->
                    var containsTags = false
                    tags.forEach { tag ->
                        if (shopping.tags.contains(tag, ignoreCase = true)) {
                            containsTags = true
                            return@forEach
                        }
                    }
                    containsTags
                })
            }
            return@map Resource.Success(result)
        }
    }
}
