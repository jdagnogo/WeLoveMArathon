package com.jdagnogo.welovemarathon.restaurant.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

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
                    currentCategorySelected = partialState.data,
                )
            }

            is RestaurantPartialState.OnFoodsSuccess -> {
                state.copy(
                    recommendedItems = partialState.recommendedItems,
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
        }
    }
}