package com.jdagnogo.welovemarathon.food.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.ui.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.food.domain.FoodUseCase
import com.jdagnogo.welovemarathon.food.domain.restaurant.Restaurant
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
        dispatchEvent(FoodUiEvent.FetchRestaurants)
    }

    override fun dispatchEvent(event: FoodUiEvent) {
        when (event) {
            FoodUiEvent.FetchRestaurants -> fetchRestaurants()
            is FoodUiEvent.OnCategorySelected -> onCategorySelected()
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
                        //Todo : hanlde error
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
    val restaurants: List<Restaurant> = listOf(),
    val currentCategory: FoodCategory = FoodCategory.RESTAURANT,
    val error: String = "",
)
@Keep
sealed class FoodPartialState {
    object Loading : FoodPartialState()
    data class Error(val message: String) : FoodPartialState()
    data class OnCategorySelected(val category: FoodCategory) : FoodPartialState()
    data class OnRestaurantsSuccess(val data: List<Restaurant>) : FoodPartialState()
}
@Keep
sealed class FoodUiEvent {
    data class OnCategorySelected(val id: String) : FoodUiEvent()
    object FetchRestaurants : FoodUiEvent()
}
