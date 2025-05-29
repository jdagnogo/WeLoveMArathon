package com.jdagnogo.welovemarathon.culture.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class CultureReducer : IReducer<CultureState, CulturePartialState> {
    override fun reduce(state: CultureState, partialState: CulturePartialState): CultureState {
        return when (partialState) {
            is CulturePartialState.Error -> state.copy()
            is CulturePartialState.OnCultureSuccess -> state.copy(
                cultures = partialState.cultures,
            )
            CulturePartialState.Loading -> state.copy()
            is CulturePartialState.OnCulturesBarsRecommendedSuccess -> {
                state.copy(
                    recommendedItems = partialState.recommendedItems,
                )
            }
            is CulturePartialState.OnRecommendedDialog -> {
                state.copy(
                    shouldOpenRecommendedDialog = partialState.item != null,
                    currentCultureSelected = partialState.item
                )
            }
            is CulturePartialState.OnCultureSelected -> {
                state.copy(
                    currentSelected = partialState.culture,
                )
            }
        }
    }
}
