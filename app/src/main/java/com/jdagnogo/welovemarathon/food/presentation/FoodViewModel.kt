package com.jdagnogo.welovemarathon.food.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.ui.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.handleResource
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.food.domain.FoodUseCase
import com.jdagnogo.welovemarathon.food.domain.restaurant.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    init {
        dispatchEvent(FoodUiEvent.FetchData)
    }

    override fun dispatchEvent(event: FoodUiEvent) {
        when (event) {
            FoodUiEvent.FetchData -> {
                fetchRestaurants()
                fetchCoffee()
                fetchDesserts()
            }
            is FoodUiEvent.OnCategorySelected -> onCategorySelected()
        }
    }

    private fun fetchDesserts() {
        viewModelScope.launch {
            handleResource(foodUseCase.getDessertsUseCase.invoke(),
                { FoodPartialState.OnDessertsSuccess(it) },
                FoodPartialState.Loading,
                { FoodPartialState.Error("") },
                { _state.value = reducer.reduce(state.value, it) },
                this)
        }
    }

    private fun fetchCoffee() {
        viewModelScope.launch {
            handleResource(foodUseCase.getCoffeeUseCase.invoke(),
                { FoodPartialState.OnCoffeeSuccess(it) },
                FoodPartialState.Loading,
                { FoodPartialState.Error("") },
                { _state.value = reducer.reduce(state.value, it) },
                this)
        }
    }

    private fun fetchRestaurants() {
        viewModelScope.launch {
            foodUseCase.getRestaurantUseCase.invoke().onEach { resource ->
                val partialState = when (resource) {
                    is Resource.Success -> {
                        FoodPartialState.OnRestaurantsSuccess(resource.data ?: listOf())
                    }
                    is Resource.Loading -> FoodPartialState.Loading
                    else -> {
                        FoodPartialState.Error("")
                    }
                }
                _state.value = reducer.reduce(_state.value, partialState)
            }.launchIn(this)
        }
    }

    private fun onCategorySelected() {

    }
}

@Keep
data class FoodState(
    val categories: List<FoodCategory> = listOf(),
    val restaurants: List<Food> = listOf(),
    val coffees: List<Food> = listOf(),
    val deserts: List<Food> = listOf(),
    val currentCategory: FoodCategory = FoodCategory.RESTAURANT,
    val error: String = "",
)

@Keep
sealed class FoodPartialState {
    object Loading : FoodPartialState()
    data class Error(val message: String) : FoodPartialState()
    data class OnRestaurantsSuccess(val data: List<Food>) : FoodPartialState()
    data class OnDessertsSuccess(val data: List<Food>) : FoodPartialState()
    data class OnCoffeeSuccess(val data: List<Food>) : FoodPartialState()
}

@Keep
sealed class FoodUiEvent {
    data class OnCategorySelected(val id: String) : FoodUiEvent()
    object FetchData : FoodUiEvent()
}
