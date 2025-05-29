package com.jdagnogo.welovemarathon.beach.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class BeachReducer : IReducer<BeachState, BeachPartialState> {
    override fun reduce(state: BeachState, partialState: BeachPartialState): BeachState {
        return when (partialState) {
            is BeachPartialState.Error -> state.copy()
            is BeachPartialState.OnBeachSuccess -> state.copy(
                beaches = partialState.beaches,
            )
            BeachPartialState.Loading -> state.copy()
            is BeachPartialState.OnBeachesBarsSuccess -> {
                state.copy(
                    categories = partialState.categories,
                    currentSelected = partialState.currentSelected,
                    shouldDisplayFilter = partialState.shouldDisplayFilter
                )
            }
            is BeachPartialState.OnBeachesBarsRecommendedSuccess -> {
                state.copy(
                    recommendedItems = partialState.recommendedItems,
                )
            }
            is BeachPartialState.OnRecommendedDialog -> {
                state.copy(
                    shouldOpenRecommendedDialog = partialState.item != null,
                    currentBeachSelected = partialState.item
                )
            }
            is BeachPartialState.OnTagSuccess ->{
                state.copy(tags = partialState.data)
            }
            is BeachPartialState.OnFilterDialog -> {
                state.copy(shouldOpenFilterDialog = partialState.isVisible)
            }
            is BeachPartialState.OnFiltersSelected -> {
                state.copy(
                    shouldOpenFilterDialog = false,
                    currentSelectedTags = partialState.data
                )
            }
        }
    }
}
