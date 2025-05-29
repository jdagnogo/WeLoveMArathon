package com.jdagnogo.welovemarathon.map.viewmodel

import com.jdagnogo.welovemarathon.common.utils.IReducer
import com.jdagnogo.welovemarathon.map.viewmodel.MapPartialState
import com.jdagnogo.welovemarathon.map.viewmodel.MapState

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
                    items = partialState.data,
                    currentSelected = partialState.currentSelected
                )
            }
            is MapPartialState.OnChipsSuccess -> {
                state.copy(
                    chips = partialState.data,
                    screenName = partialState.screenName
                )
            }
            is MapPartialState.OnNewCameraPosition -> {
                state.copy(
                    zoom = partialState.zoom,
                    currentPosition = partialState.position
                )
            }
            is MapPartialState.OnNewMapType -> {
                state.copy(
                    currentType = partialState.mapType
                )
            }
        }
    }
}
