package com.jdagnogo.welovemarathon.wine.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class WineReducer : IReducer<WineState, WinePartialState> {
    override fun reduce(state: WineState, partialState: WinePartialState): WineState {
        return when (partialState) {
            is WinePartialState.Error -> {
                state.copy()
            }
            WinePartialState.Loading -> {
                state.copy()
            }
            is WinePartialState.OnWineSocialSuccess -> {
                state.copy(
                    socials = partialState.data
                )
            }
            is WinePartialState.OnWineTourSuccess -> {
                state.copy(
                    tours = partialState.data
                )
            }
        }
    }
}