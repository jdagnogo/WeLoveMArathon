package com.jdagnogo.welovemarathon.food.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class FoodReducer : IReducer<FoodState, FoodPartialState> {
    override fun reduce(state: FoodState, partialState: FoodPartialState): FoodState {
        return when (partialState) {
            is FoodPartialState.Error -> {
                state.copy()
            }
            is FoodPartialState.OnFoodsSuccess -> {
                state.copy(
                    foods = partialState.items,
                    recommendedItems = partialState.recommendedItems,
                )
            }
            is FoodPartialState.OnCategoriesSuccess -> {
                state.copy(
                    categories = partialState.data
                )
            }
            FoodPartialState.Loading -> {
                state.copy()
            }
            is FoodPartialState.OnBannerSuccess -> {
                state.copy(
                    banner = partialState.banner
                )
            }
            is FoodPartialState.OnRecommendedDialog -> {
                state.copy(
                    shouldOpenRecommendedDialog = partialState.item != null,
                    currentFoodSelected = partialState.item
                )
            }
            is FoodPartialState.OnTagSuccess -> {
                state.copy(tags = partialState.data)
            }
            is FoodPartialState.OnFilterDialog -> {
                state.copy(shouldOpenFilterDialog = partialState.isVisible)
            }
            is FoodPartialState.OnFiltersSelected -> {
                state.copy(
                    shouldOpenFilterDialog = false,
                    currentSelectedTags = partialState.data
                )
            }
            is FoodPartialState.OnCategoriesSelected -> {
                state.copy(
                    currentSelectedTags = emptyList(),
                    currentSelected = partialState.data,
                )
            }
            FoodPartialState.OnFilterReset -> {
                state.copy(
                    shouldOpenFilterDialog = false,
                    currentSelectedTags = emptyList()
                )
            }
        }
    }
}
