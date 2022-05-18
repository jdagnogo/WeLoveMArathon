package com.jdagnogo.welovemarathon.map.viewmodel

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.handleResource
import com.jdagnogo.welovemarathon.map.domain.MapChip
import com.jdagnogo.welovemarathon.map.domain.MapItem
import com.jdagnogo.welovemarathon.map.domain.MapType
import com.jdagnogo.welovemarathon.map.domain.MapUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val useCases: MapUseCases,
    private val reducer: MapReducer,
) : ViewModel(), IModel<MapState, MapUiEvent> {

    private val _state = MutableStateFlow(MapState())
    override val state: StateFlow<MapState> get() = _state

    override fun dispatchEvent(event: MapUiEvent) {
        when (event) {
            is MapUiEvent.OnCategorySelected -> {
                onCategorySelected(state.value.currentType, event.key)
            }
            is MapUiEvent.OnInit -> {
                onInit(event.type)
            }
            is MapUiEvent.OnMarkerSelected -> {
                onMarkerSelected(event.mapItem)
            }
        }
    }

    private fun onCategorySelected(type: String, key: String?) {
        when (type) {
            MapType.Food.key -> {
                fetchData(
                    useCase = { useCases.getFoodUseCase.invoke(key) },
                    mapper = { it.map { food -> food.toMapItem() } },
                    currentSelected = key
                )
            }
            MapType.Shopping.key -> {
                fetchData(
                    useCase = { useCases.getShoppingUseCase.invoke(key) },
                    mapper = { it.map { shopping -> shopping.toMapItem() } },
                    currentSelected = key
                )
            }
            MapType.Beach.key -> {
                fetchData(
                    useCase = { useCases.getBeachesBarUseCase.invoke(key) },
                    mapper = { it.map { beach -> beach.toMapItem() } },
                    currentSelected = key
                )
            }
        }
    }

    private fun onMarkerSelected(mapItem: MapItem) {
        val partialState = MapPartialState.OnNewCameraPosition(
            zoom = 14f,
            position = mapItem.latLng
        )
        _state.value = reducer.reduce(state.value, partialState)
    }

    private fun onInit(type: String) {
        if (state.value.currentType != type) {
            loadData(type)
            _state.value = reducer.reduce(state.value, MapPartialState.OnNewMapType(type))
        } else {
            return
        }
    }

    private fun loadData(type: String) {
        when (type) {
            MapType.Food.key -> {
                fetchData(
                    useCase = { useCases.getFoodUseCase.invoke() },
                    mapper = { it.map { food -> food.toMapItem() } },
                )

                fetchCategory(
                    screenName = MapType.Food.screenName,
                    useCase = { useCases.getFoodCategoriesUseCase.invoke() },
                    mapper = { createAllChip().plus(it.map { food -> food.toMapChip() }) },
                )
            }
            MapType.Shopping.key -> {
                fetchData(
                    useCase = { useCases.getShoppingUseCase.invoke() },
                    mapper = { it.map { shopping -> shopping.toMapItem() } },
                )

                fetchCategory(
                    screenName = MapType.Shopping.screenName,
                    useCase = { useCases.getShoppingCategoriesUseCase.invoke() },
                    mapper = { createAllChip().plus(it.map { shopping -> shopping.toMapChip() }) },
                )
            }
            MapType.Beach.key -> {
                fetchData(
                    useCase = { useCases.getBeachesBarUseCase.invoke() },
                    mapper = { it.map { beach -> beach.toMapItem() } },
                )

                fetchCategory(
                    screenName = MapType.Beach.screenName,
                    useCase = { useCases.getBeachesUseCase.invoke() },
                    mapper = { createAllChip().plus(it.map { beach -> beach.toMapChip() }) },
                )
            }
        }
    }

    private fun createAllChip(): List<MapChip> {
        return listOf(MapChip("All"))
    }

    private fun <T> fetchCategory(
        screenName: String,
        useCase: suspend () -> Flow<Resource<List<T>>>,
        mapper: (List<T>) -> List<MapChip>
    ) {
        viewModelScope.launch {
            handleResource(
                useCase.invoke(),
                {
                    MapPartialState.OnChipsSuccess(
                        mapper(it),
                        screenName = screenName
                    )
                },
                MapPartialState.Loading,
                { MapPartialState.Error("") },
                { partialState ->
                    _state.value = reducer.reduce(state.value, partialState)
                },
                this
            )
        }
    }

    private fun <T> fetchData(
        useCase: suspend () -> Flow<Resource<List<T>>>,
        mapper: (List<T>) -> List<MapItem>,
        currentSelected: String? = null,
    ) {
        viewModelScope.launch {
            handleResource(
                useCase.invoke(),
                { MapPartialState.OnDataSuccess(mapper(it), currentSelected) },
                MapPartialState.Loading,
                { MapPartialState.Error("") },
                { partialState ->
                    _state.value = reducer.reduce(state.value, partialState)
                },
                this
            )
        }
    }
}

const val START_LATITUDE = 38.143414
const val START_LONGITUDE = 23.9830504

@Keep
data class MapState(
    val error: String = "",
    val screenName: String = "",
    val currentPosition: LatLng = LatLng(START_LATITUDE, START_LONGITUDE),
    val zoom: Float = 10f,
    val chips: List<MapChip> = emptyList(),
    val items: List<MapItem> = emptyList(),
    val currentSelected: String? = null,
    val currentType: String = "",
)

sealed class MapEffect {

}

@Keep
sealed class MapPartialState {
    object Loading : MapPartialState()
    data class Error(val message: String) : MapPartialState()
    data class OnNewMapType(val mapType: String) : MapPartialState()
    data class OnDataSuccess(
        val data: List<MapItem>,
        val currentSelected: String?
    ) : MapPartialState()

    data class OnNewCameraPosition(val zoom: Float, val position: LatLng) : MapPartialState()
    data class OnChipsSuccess(val data: List<MapChip>, val screenName: String) : MapPartialState()
}

@Keep
sealed class MapUiEvent {
    data class OnCategorySelected(val key: String?) : MapUiEvent()
    data class OnMarkerSelected(val mapItem: MapItem) : MapUiEvent()
    data class OnInit(val type: String) : MapUiEvent()
}
