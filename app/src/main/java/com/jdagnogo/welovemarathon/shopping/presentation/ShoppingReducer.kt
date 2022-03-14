package com.jdagnogo.welovemarathon.shopping.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class ShoppingReducer : IReducer<ShoppingState, ShoppingPartialState> {
    override fun reduce(state: ShoppingState, partialState: ShoppingPartialState): ShoppingState {
        return when (partialState) {
            is ShoppingPartialState.Error -> {
                state.copy()
            }
            is ShoppingPartialState.OnShoppingsSuccess -> {
                state.copy(
                    shoppings = partialState.items,
                    recommendedItems = partialState.recommendedItems,
                )
            }
            is ShoppingPartialState.OnCategoriesSuccess -> {
                state.copy(
                    categories = partialState.data
                )
            }
            ShoppingPartialState.Loading -> {
                state.copy()
            }
            is ShoppingPartialState.OnBannerSuccess -> {
                state.copy(
                    banner = partialState.banner
                )
            }
            is ShoppingPartialState.OnRecommendedDialog -> {
                state.copy(
                    shouldOpenRecommendedDialog = partialState.item != null,
                    currentShoppingSelected = partialState.item
                )
            }
            is ShoppingPartialState.OnTagSuccess -> {
                state.copy(tags = partialState.data)
            }
            is ShoppingPartialState.OnFilterDialog -> {
                state.copy(shouldOpenFilterDialog = partialState.isVisible)
            }
            is ShoppingPartialState.OnFiltersSelected -> {
                state.copy(
                    shouldOpenFilterDialog = false,
                    currentSelectedTags = partialState.data
                )
            }
            is ShoppingPartialState.OnCategoriesSelected -> {
                state.copy(
                    currentSelectedTags = emptyList(),
                    currentSelected = partialState.data,
                )
            }
        }
    }
}
