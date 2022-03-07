package com.jdagnogo.welovemarathon.map

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.map.domain.MapChip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapViewModel : ViewModel(), IModel<MapState, MapPartialState> {
    private val _state = MutableStateFlow(
        MapState(chips =
        listOf(
            MapChip(id = "toto1", iconUrl = "", "toto1"),
            MapChip(id = "toto2", iconUrl = "", "toto2"),
            MapChip(id = "toto3", iconUrl = "", "toto3"),
            MapChip(id = "toto4", iconUrl = "", "toto4"),
        ),
        currentSelected = "toto2")
    )
    override val state: StateFlow<MapState> get() = _state

    override fun dispatchEvent(event: MapPartialState) {

    }
}

@Keep
data class MapState(
    val error: String = "",
    val chips : List<MapChip>,
    val currentSelected: String = "",
)

@Keep
sealed class MapPartialState {

}

sealed class MapUiEvent {

}
