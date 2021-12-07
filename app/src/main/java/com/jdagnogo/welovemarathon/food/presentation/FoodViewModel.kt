package com.jdagnogo.welovemarathon.food.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.ui.IModel
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.food.domain.FoodUseCase
import com.jdagnogo.welovemarathon.food.domain.restaurant.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodUseCase: FoodUseCase,
    private val reducer: FoodReducer,
) : ViewModel(),
    IModel<FoodState, FoodUiEvent> {
    private val _state = MutableStateFlow(FoodState())
    override val state: StateFlow<FoodState> get() = _state

    private var currentSelected: String? = null

    init {
        viewModelScope.launch {
            delay(500)
            dispatchEvent(FoodUiEvent.OnCategorySelected(FoodCategory.RESTAURANT.title))
        }
    }

    override fun dispatchEvent(event: FoodUiEvent) {
        when (event) {
            is FoodUiEvent.OnCategorySelected -> onCategorySelected(event.type)
        }
    }

    private suspend fun fetchOthers(type: String) {
        if (currentSelected == type) return
        val data = foodUseCase.getFoodOthersUseCase.invoke(type)
        val partialState = FoodPartialState.OnOthersSuccess(data = data)
        _state.value = reducer.reduce(_state.value, partialState)
    }

    private suspend fun fetchRecommended(type: String) {
        if (currentSelected == type) return
        val data = foodUseCase.getFoodRecommendedUseCase.invoke(type)
        val partialState = FoodPartialState.OnRecommendedSuccess(data = data)
        _state.value = reducer.reduce(_state.value, partialState)
    }

    private fun onCategorySelected(type: String) {
        viewModelScope.launch {
            fetchRecommended(type = type)
            fetchOthers(type = type)
            currentSelected = type
        }
    }
}

@Keep
data class FoodState(
    val categories: List<FoodCategory> = listOf(),
    val recommended: List<Food> = listOf(),
    val others: List<Food> = listOf(),
    val currentCategory: FoodCategory = FoodCategory.RESTAURANT,
    val error: String = "",
)

@Keep
sealed class FoodPartialState {
    object Loading : FoodPartialState()
    data class Error(val message: String) : FoodPartialState()
    data class OnRecommendedSuccess(val data: List<Food>) : FoodPartialState()
    data class OnOthersSuccess(val data: List<Food>) : FoodPartialState()
}

@Keep
sealed class FoodUiEvent {
    data class OnCategorySelected(val type: String) : FoodUiEvent()
}
