package com.jdagnogo.welovemarathon.shopping.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.shopping.data.ShoppingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetShoppingUseCase @Inject constructor(
    private val repository: ShoppingRepository,
    private val coroutineScope: CoroutineScope,
) {
    private val _data: MutableStateFlow<Resource<List<Shopping>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    val data: StateFlow<Resource<List<Shopping>>> = _data

    init {
        coroutineScope.launch {
            repository.getShoppings(true).collectLatest {
                _data.value = it
            }
        }
    }

    fun getShopping(
        shoppingCategories: ShoppingCategories,
    ): List<Shopping> {
        return data.value.data?.filter { data -> data.category == shoppingCategories } ?: listOf()
    }
}
