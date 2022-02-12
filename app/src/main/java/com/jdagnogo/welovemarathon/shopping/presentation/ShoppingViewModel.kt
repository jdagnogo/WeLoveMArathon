package com.jdagnogo.welovemarathon.shopping.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.shopping.domain.GetShoppingUseCase
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val useCases: GetShoppingUseCase,
    private val reducer: ShoppingReducer,
) : ViewModel(), IModel<ShoppingState, ShoppingUiEvent> {

    private val _state = MutableStateFlow(ShoppingState())
    override val state: StateFlow<ShoppingState> get() = _state

    private var currentSelected: ShoppingCategories? = null

    init {
        viewModelScope.launch {
            delay(1000)
            dispatchEvent(ShoppingUiEvent.OnCategoryClicked(ShoppingCategories.Woman))
        }
    }

    private fun fetchShoppings(category: ShoppingCategories) {
        viewModelScope.launch {
            if (currentSelected == category) return@launch
            currentSelected = category
            val data = useCases.getShopping(category)
            val recommended = data.firstOrNull { it.isRecommended }
            data.toMutableList().remove(recommended)
            val partialState = ShoppingPartialState.OnShoppingsSuccess(data = data,
                recommended,
                category)
            _state.value = reducer.reduce(_state.value, partialState)
        }
    }

    override fun dispatchEvent(event: ShoppingUiEvent) {
        when (event) {
            is ShoppingUiEvent.OnCategoryClicked -> fetchShoppings(category = event.category)
        }
    }
}

/**
 * The data class that will describe the state of the view
 */
@Keep
data class ShoppingState(
    val currentSelected: ShoppingCategories = ShoppingCategories.Woman,
    val shoppings: List<Shopping> = listOf(),
    val recommended: Shopping? = null,
    val error: String = "",
)

@Keep
sealed class ShoppingPartialState {
    data class Error(val message: String) : ShoppingPartialState()
    data class OnShoppingsSuccess(
        val data: List<Shopping>,
        val recommended: Shopping?,
        val currentSelected: ShoppingCategories,
    ) :
        ShoppingPartialState()
}

/**
 *  Here Intent is the same as Intent in MVI architecture
 *  Google likes to call it Event as Android already has an Intent class
 */
sealed class ShoppingUiEvent {
    data class OnCategoryClicked(val category: ShoppingCategories) : ShoppingUiEvent()
}
