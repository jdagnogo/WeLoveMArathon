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
        }
    }
}
