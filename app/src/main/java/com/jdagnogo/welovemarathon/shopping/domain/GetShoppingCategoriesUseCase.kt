package com.jdagnogo.welovemarathon.shopping.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.shopping.data.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShoppingCategoriesUseCase @Inject constructor(private val repository: ShoppingRepository) {
    operator fun invoke(): Flow<Resource<List<ShoppingCategory>>> {
        return repository.categories.transformContent { categories ->
            categories?.sortedBy { it.ordinal } ?: listOf()
        }
    }
}

fun <T, R> Flow<Resource<T>>.transformContent(transform: (T?) -> R): Flow<Resource<R>> {
    return map { it.transformContent(transform) }
}


//FIXME : move it to common
fun <T, R> Resource<T>.transformContent(transform: (T?) -> R): Resource<R> {
    return when (this) {
        is Resource.Loading -> Resource.Loading()
        is Resource.Success -> Resource.Success(transform(data))
        else -> Resource.Error("") //FIXMe : map type of error
    }
}
