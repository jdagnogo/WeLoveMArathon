package com.jdagnogo.welovemarathon.activities.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class ActivitiesReducer : IReducer<ActivitiesState, ActivitiesPartialState> {
    override fun reduce(state: ActivitiesState, partialState: ActivitiesPartialState): ActivitiesState {
        return when (partialState) {
            is ActivitiesPartialState.Error -> {
                state.copy()
            }
            is ActivitiesPartialState.OnActivitiessSuccess -> {
                state.copy(
                    activitiess = partialState.items,
                    recommendedItems = partialState.recommendedItems,
                    shouldDisplayFilter = partialState.shouldDisplayFilter
                )
            }
            is ActivitiesPartialState.OnCategoriesSuccess -> {
                state.copy(
                    categories = partialState.data
                )
            }
            ActivitiesPartialState.Loading -> {
                state.copy()
            }
            is ActivitiesPartialState.OnBannerSuccess -> {
                state.copy(
                    banner = partialState.banner
                )
            }
            is ActivitiesPartialState.OnRecommendedDialog -> {
                state.copy(
                    shouldOpenRecommendedDialog = partialState.item != null,
                    currentActivitiesSelected = partialState.item
                )
            }
            is ActivitiesPartialState.OnTagSuccess -> {
                state.copy(tags = partialState.data)
            }
            is ActivitiesPartialState.OnFilterDialog -> {
                state.copy(shouldOpenFilterDialog = partialState.isVisible)
            }
            is ActivitiesPartialState.OnFiltersSelected -> {
                state.copy(
                    shouldOpenFilterDialog = false,
                    currentSelectedTags = partialState.data
                )
            }
            is ActivitiesPartialState.OnCategoriesSelected -> {
                state.copy(
                    currentSelectedTags = emptyList(),
                    currentSelected = partialState.data,
                )
            }
            ActivitiesPartialState.OnFilterReset -> {
                state.copy(
                    shouldOpenFilterDialog = false,
                    currentSelectedTags = emptyList()
                )
            }
        }
    }
}
