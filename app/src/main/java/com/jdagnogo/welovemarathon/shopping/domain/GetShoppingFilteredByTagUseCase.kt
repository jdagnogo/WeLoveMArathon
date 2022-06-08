package com.jdagnogo.welovemarathon.shopping.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class GetShoppingFilteredByTagUseCase(private val delegate: GetShoppingUseCase) {
    operator fun invoke(
        type: String? = null,
        tags: List<String> = emptyList()
    ): Flow<Resource<List<Shopping>>> {


        return delegate.invoke(type).transformContent { result ->
            result?.filter { shopping ->
                var containsTags = false
                tags.forEach { tag ->
                    if (shopping.tags.contains(tag, ignoreCase = true)) {
                        containsTags = true
                        return@forEach
                    }
                }
                containsTags
            } ?: emptyList()
        }
    }
}