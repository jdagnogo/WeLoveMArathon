package com.jdagnogo.welovemarathon.restaurant.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer
import com.jdagnogo.welovemarathon.food.domain.FoodCategory

class RestaurantReducer : IReducer<RestaurantState, RestaurantPartialState> {
    override fun reduce(
        state: RestaurantState,
        partialState: RestaurantPartialState
    ): RestaurantState {
        return when (partialState) {
            is RestaurantPartialState.Error -> {
                state.copy()
            }

            RestaurantPartialState.Loading -> {
                state.copy()
            }

            is RestaurantPartialState.OnCategoriesSuccess -> {
                state.copy(
                    categories = partialState.data,
                )
            }

            is RestaurantPartialState.OnCategoriesSelected -> {
                state.copy(
                    currentFilterApplied = partialState.filter,
                    currentCategorySelected = partialState.data,
                )
            }

            is RestaurantPartialState.OnRestaurantsSuccess -> {
                state.copy(
                    items = partialState.restaurants,
                )
            }

            RestaurantPartialState.OnRestaurantReset -> {
                state.copy(
                    currentRestaurantSelected = null,
                )
            }

            is RestaurantPartialState.OnRestaurantSelected -> {
                state.copy(
                    currentRestaurantSelected = partialState.data,
                )
            }

            is RestaurantPartialState.OnValidateFilter -> {
                state.copy(
                    currentFilterApplied = partialState.filter,
                    currentCategorySelected = FoodCategory.allCategory,
                )
            }

            is RestaurantPartialState.OnRestaurantFilterSuccess -> {
                state.copy(
                    filter = partialState.data.firstOrNull(),
                )
            }
        }
    }
}