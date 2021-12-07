package com.jdagnogo.welovemarathon.food.presentation

import com.jdagnogo.welovemarathon.common.ui.IReducer

class FoodReducer : IReducer<FoodState, FoodPartialState> {
    override fun reduce(state: FoodState, partialState: FoodPartialState): FoodState {
        return when (partialState) {
            is FoodPartialState.Error -> state.copy()
            FoodPartialState.Loading -> state.copy()
            is FoodPartialState.OnOthersSuccess -> state.copy(others = partialState.data)
            is FoodPartialState.OnRecommendedSuccess -> state.copy(recommended = partialState.data)
        }
    }
}
