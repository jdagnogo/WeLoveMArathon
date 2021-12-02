package com.jdagnogo.welovemarathon.shopping.presentation

import com.jdagnogo.welovemarathon.common.ui.IReducer

class ShoppingReducer : IReducer<ShoppingState, ShoppingPartialState> {
    override fun reduce(state: ShoppingState, partialState: ShoppingPartialState): ShoppingState {
        return when (partialState) {
            is ShoppingPartialState.Error -> {
                state.copy()
            }
            is ShoppingPartialState.OnShoppingsSuccess -> {
                state.copy(
                    currentSelected = partialState.currentSelected,
                    shoppings = partialState.data,
                    recommended = partialState.recommended
                )
            }
        }
    }
}
