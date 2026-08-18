package com.jdagnogo.welovemarathon.events.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class EventsReducer : IReducer<EventsState, EventsPartialState> {
    override fun reduce(state: EventsState, partialState: EventsPartialState): EventsState {
        return when (partialState) {
            EventsPartialState.Loading -> state.copy(isLoading = true)
            is EventsPartialState.OnEventsSuccess -> state.copy(
                events = partialState.data,
                isLoading = false,
            )
            is EventsPartialState.Error -> state.copy(isLoading = false, error = partialState.message)
            is EventsPartialState.OnSearchQueryChanged -> state.copy(searchQuery = partialState.query)
            is EventsPartialState.OnCategorySelected -> state.copy(
                selectedCategory = if (state.selectedCategory == partialState.category) null else partialState.category,
            )
            EventsPartialState.OnAllCategoriesSelected -> state.copy(selectedCategory = null)
        }
    }
}
