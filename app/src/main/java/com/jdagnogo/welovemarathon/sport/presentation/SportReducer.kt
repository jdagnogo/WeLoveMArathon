package com.jdagnogo.welovemarathon.sport.presentation

import com.jdagnogo.welovemarathon.common.ui.IReducer

class SportReducer : IReducer<SportState, SportPartialState> {
    override fun reduce(state: SportState, partialState: SportPartialState): SportState {
        return when (partialState) {
            is SportPartialState.Error -> state.copy()
            SportPartialState.Loading -> state.copy()
            is SportPartialState.OnCategoriesSuccess -> state.copy(categories = partialState.data)
            is SportPartialState.OnSportsSuccess -> state.copy(sports = partialState.data)
        }
    }
}
