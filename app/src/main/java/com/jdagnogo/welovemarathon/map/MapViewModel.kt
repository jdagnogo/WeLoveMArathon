package com.jdagnogo.welovemarathon.map

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.handleResource
import com.jdagnogo.welovemarathon.map.domain.MapChip
import com.jdagnogo.welovemarathon.map.domain.MapItem
import com.jdagnogo.welovemarathon.map.domain.MapUseCases
import com.jdagnogo.welovemarathon.map.presentation.MapReducer
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
            }
            is MapUiEvent.OnInit -> {
                onInit(event.type)
            }
        }
    }

    private fun onInit(type: MapType) {
        when (type) {
            MapType.Food -> {
            }
            MapType.Shopping -> {
                fetchData(
                    useCase = { useCases.getShoppingUseCase.invoke() },
                    mapper = {
                        it.map { shopping ->
                            MapItem(
                                name = shopping.name,
                                tags = shopping.tags,
                                latLng = LatLng(
                                    shopping.coordinate?.latitude ?: 0.0,
                                    shopping.coordinate?.longitude ?: 0.0,
                                )
                            )
                        }
                    },
                )

                fetchCategory(
                    useCase = { useCases.getShoppingCategoriesUseCase.invoke() },
                    mapper = {
                        it.map { shopping ->
                            MapChip(
                                name = shopping.name,
                                iconUrl = shopping.icon
                            )
                        }
                    },
                )
            }
        }
    }

    private fun <T> fetchCategory(
        useCase: suspend () -> Flow<Resource<List<T>>>,
        mapper: (List<T>) -> List<MapChip>
    ) {
        viewModelScope.launch {
            handleResource(
                useCase.invoke(),
                {
                    MapPartialState.OnChipsSuccess(
                        mapper(it)
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
        mapper: (List<T>) -> List<MapItem>
    ) {
        viewModelScope.launch {
            handleResource(
                useCase.invoke(),
                {
                    MapPartialState.OnDataSuccess(
                        mapper(it)
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
}

@Keep
data class MapState(
    val error: String = "",
    val chips: List<MapChip> = emptyList(),
    val items: List<MapItem> = emptyList(),
    val currentSelected: String = "",
)

@Keep
sealed class MapPartialState {
    object Loading : MapPartialState()
    data class Error(val message: String) : MapPartialState()
    data class OnDataSuccess(val data: List<MapItem>) : MapPartialState()
    data class OnChipsSuccess(val data: List<MapChip>) : MapPartialState()
}
@Keep
sealed class MapUiEvent {
    data class OnCategorySelected(val id: String) : MapUiEvent()
    data class OnInit(val type: MapType) : MapUiEvent()
}

sealed class MapType {
    object Shopping : MapType()
    object Food : MapType()
}
