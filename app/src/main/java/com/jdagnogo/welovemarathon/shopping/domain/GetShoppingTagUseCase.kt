package com.jdagnogo.welovemarathon.shopping.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.shopping.data.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingTagUseCase @Inject constructor(private val repository: ShoppingRepository) {
    operator fun invoke(category: String): Flow<Resource<List<ShoppingTag>>> {
        return repository.tags.transformContent { categories ->
            categories?.sortedBy { it.name }
                ?.filter {
                    it.category.contains(category, ignoreCase = true)
                } ?: listOf()
        }
    }
}
