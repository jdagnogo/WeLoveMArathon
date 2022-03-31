package com.jdagnogo.welovemarathon.map.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer
import com.jdagnogo.welovemarathon.map.MapPartialState
import com.jdagnogo.welovemarathon.map.MapState

class MapReducer : IReducer<MapState, MapPartialState> {
    override fun reduce(state: MapState, partialState: MapPartialState): MapState {
        return when (partialState) {
            is MapPartialState.Error -> {
                state.copy()
            }
            MapPartialState.Loading -> {
                state.copy()
            }
            is MapPartialState.OnDataSuccess -> {
                state.copy(
                    items = partialState.data
                )
            }
            is MapPartialState.OnChipsSuccess -> {
                state.copy(
                    chips = partialState.data,
                    screenName = partialState.screenName
                )
            }
        }
    }
}
