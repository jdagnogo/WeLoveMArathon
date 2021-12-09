package com.jdagnogo.welovemarathon.food.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.ui.IModel
import com.jdagnogo.welovemarathon.common.utils.handleResource
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
            dispatchEvent(FoodUiEvent.OnCategorySelected(FoodCategory.RESTAURANT.title))
        }
    }

    override fun dispatchEvent(event: FoodUiEvent) {
        when (event) {
            is FoodUiEvent.OnCategorySelected -> onCategorySelected(event.type)
        }
    }

    private fun fetchOthers(type: String) {
        if (currentSelected == type) return
        viewModelScope.launch {
            handleResource(foodUseCase.getFoodOthersUseCase.invoke(type),
                { FoodPartialState.OnOthersSuccess(it) },
                FoodPartialState.Loading,
                { FoodPartialState.Error("") },
                { _state.value = reducer.reduce(state.value, it) },
                this)
        }
    }

    private fun fetchRecommended(type: String) {
        if (currentSelected == type) return
        viewModelScope.launch {
            handleResource(foodUseCase.getFoodRecommendedUseCase.invoke(type),
                { FoodPartialState.OnRecommendedSuccess(it) },
                FoodPartialState.Loading,
                { FoodPartialState.Error("") },
                { _state.value = reducer.reduce(state.value, it) },
                this)
        }
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
