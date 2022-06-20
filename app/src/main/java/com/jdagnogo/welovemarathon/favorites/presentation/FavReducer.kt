package com.jdagnogo.welovemarathon.favorites.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class FavReducer : IReducer<FavViewModel.FavState, FavViewModel.FavPartialState> {
    override fun reduce(state: FavViewModel.FavState, partialState: FavViewModel.FavPartialState): FavViewModel.FavState {
        return when (partialState) {
            is FavViewModel.FavPartialState.Error -> {
                state.copy()
            }
            is FavViewModel.FavPartialState.OnFavSuccess -> {
                state.copy(
                    favorites = partialState.favorites
                )
            }
        }
    }
}