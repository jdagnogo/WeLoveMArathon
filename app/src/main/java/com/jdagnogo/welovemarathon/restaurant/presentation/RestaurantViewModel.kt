package com.jdagnogo.welovemarathon.restaurant.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.handleResource
import com.jdagnogo.welovemarathon.common.utils.handleResourceWithFav
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.food.domain.FoodCategory.Companion.ALL
import com.jdagnogo.welovemarathon.food.domain.FoodCategory.Companion.allCategory
import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantAppliedFilter
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantFilter
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val useCases: RestaurantUseCase,
    private val reducer: RestaurantReducer,
) : ViewModel(), IModel<RestaurantState, RestaurantUiEvent> {
    private val _state = MutableStateFlow(RestaurantState())
    override val state: StateFlow<RestaurantState> get() = _state

    init {
        fetchCategories()
        fetchFilters()
        dispatchEvent(event = RestaurantUiEvent.OnCategoryClicked(ALL))
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            handleResource(
                useCases.getFoodCategoriesUseCase.invoke(),
                {
                    RestaurantPartialState.OnCategoriesSuccess(it)
                },
                RestaurantPartialState.Loading,
                { RestaurantPartialState.Error("") },
                {
                    _state.value = reducer.reduce(state.value, it)
                },
                this
            )
        }
    }

    private fun fetchFilters() {
        viewModelScope.launch {
            handleResource(
                useCases.getRestaurantFilterUseCase.invoke(),
                {
                    RestaurantPartialState.OnRestaurantFilterSuccess(it)
                },
                RestaurantPartialState.Loading,
                { RestaurantPartialState.Error("") },
                {
                    _state.value = reducer.reduce(state.value, it)
                },
                this
            )
        }
    }

    private fun fetchRestaurants(filter: RestaurantAppliedFilter) {
        viewModelScope.launch {
            viewModelScope.launch {
                handleResourceWithFav(
                    useCases.getRestaurantUseCase.invoke(filter),
                    useCases.favUseCase.getAllFavUseCases(),
                    { it, favorites ->
                        RestaurantPartialState.OnRestaurantsSuccess(
                            it
                        )
                    },
                    RestaurantPartialState.Loading,
                    { RestaurantPartialState.Error("") },
                    { partialState ->
                        _state.value = reducer.reduce(state.value, partialState)
                    },
                    this
                )
            }
        }
    }

    override fun dispatchEvent(event: RestaurantUiEvent) {
        when (event) {
            is RestaurantUiEvent.OnCategoryClicked -> {
                val category =
                    state.value.allCategories.firstOrNull { it.name == event.name } ?: return
                val filter =
                    state.value.currentFilterApplied.copy(typeOfFilters = mutableSetOf(category.name))
                fetchRestaurants(filter)
                val partialState = RestaurantPartialState.OnCategoriesSelected(category, filter)
                _state.value = reducer.reduce(state.value, partialState)
            }

            is RestaurantUiEvent.OnFilterClicked -> {}
            is RestaurantUiEvent.OnLikeClicked -> {}
            is RestaurantUiEvent.OnRestaurantClicked -> {
                val restaurant = state.value.items.firstOrNull { it.id == event.id } ?: return
                val partialState = RestaurantPartialState.OnRestaurantSelected(restaurant)
                _state.value = reducer.reduce(state.value, partialState)
            }

            RestaurantUiEvent.OnRestaurantReset -> {
                val partialState = RestaurantPartialState.OnRestaurantReset
                _state.value = reducer.reduce(state.value, partialState)
            }

            is RestaurantUiEvent.OnValidateFilter -> {
                val partialState = RestaurantPartialState.OnValidateFilter(event.appliedFilter)
                fetchRestaurants(event.appliedFilter)
                _state.value = reducer.reduce(state.value, partialState)
            }
        }
    }


}

@Keep
data class RestaurantState(
    val currentCategorySelected: FoodCategory = FoodCategory(),
    val categories: List<FoodCategory> = listOf(),
    val foods: List<CategoryItem> = listOf(),
    val items: List<Restaurant> = emptyList(),
    val currentRestaurantSelected: Restaurant? = null,
    val filter: RestaurantFilter = RestaurantFilter(),
    val currentFilterApplied: RestaurantAppliedFilter = RestaurantAppliedFilter(),
) {
    val allCategories = listOf(allCategory).plus(categories)
}

@Keep
sealed class RestaurantPartialState {
    data class Error(val message: String) : RestaurantPartialState()
    data object Loading : RestaurantPartialState()
    data class OnValidateFilter(val filter: RestaurantAppliedFilter) : RestaurantPartialState()
    data class OnCategoriesSelected(val data: FoodCategory, val filter: RestaurantAppliedFilter) :
        RestaurantPartialState()

    data class OnRestaurantSelected(val data: Restaurant) : RestaurantPartialState()
    data object OnRestaurantReset : RestaurantPartialState()
    data class OnCategoriesSuccess(val data: List<FoodCategory>) : RestaurantPartialState()
    data class OnRestaurantFilterSuccess(val data: List<RestaurantFilter>) :
        RestaurantPartialState()

    data class OnRestaurantsSuccess(val restaurants: List<Restaurant>) :
        RestaurantPartialState()
}

@Keep
sealed class RestaurantUiEvent {
    data class OnCategoryClicked(val name: String) : RestaurantUiEvent()
    data class OnRestaurantClicked(val id: String) : RestaurantUiEvent()
    data object OnRestaurantReset : RestaurantUiEvent()
    data class OnValidateFilter(val appliedFilter: RestaurantAppliedFilter) : RestaurantUiEvent()
    data class OnFilterClicked(val isVisible: Boolean) : RestaurantUiEvent()
    data class OnLikeClicked(val id: String) : RestaurantUiEvent()
}