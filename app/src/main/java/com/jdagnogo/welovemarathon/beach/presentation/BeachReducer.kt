package com.jdagnogo.welovemarathon.beach.presentation

import com.jdagnogo.welovemarathon.common.ui.IReducer

class BeachReducer : IReducer<BeachState, BeachPartialState> {
    override fun reduce(state: BeachState, partialState: BeachPartialState): BeachState {
        return when (partialState) {
            is BeachPartialState.Error -> state.copy()
            is BeachPartialState.OnBeachSuccess -> state.copy(beaches = partialState.beaches)
        }
    }
}