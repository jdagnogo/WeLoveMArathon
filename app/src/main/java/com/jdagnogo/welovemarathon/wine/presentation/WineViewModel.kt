package com.jdagnogo.welovemarathon.wine.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.handleResource
import com.jdagnogo.welovemarathon.food.presentation.FoodPartialState
import com.jdagnogo.welovemarathon.wine.domain.WineSocial
import com.jdagnogo.welovemarathon.wine.domain.WineTour
import com.jdagnogo.welovemarathon.wine.domain.WineUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WineViewModel @Inject constructor(
    private val useCases: WineUseCases,
    private val reducer: WineReducer,
) : ViewModel(), IModel<WineState, WineUiEvent> {

    private val _state = MutableStateFlow(WineState())
    override val state: StateFlow<WineState> get() = _state

    init {
        fetchTour()
        fetchSocial()
    }

    private fun fetchTour() {
        viewModelScope.launch {
            handleResource(
                useCases.getWineTourUseCase.invoke(),
                { WinePartialState.OnWineTourSuccess(it) },
                WinePartialState.Loading,
                { WinePartialState.Error("") },
                {
                    _state.value = reducer.reduce(state.value, it)
                },
                this
            )
        }
    }

    private fun fetchSocial() {
        viewModelScope.launch {
            handleResource(
                useCases.getWineSocialUseCase.invoke(),
                { WinePartialState.OnWineSocialSuccess(it) },
                WinePartialState.Loading,
                { WinePartialState.Error("") },
                {
                    _state.value = reducer.reduce(state.value, it)
                },
                this
            )
        }
    }

    override fun dispatchEvent(event: WineUiEvent) {
        when (event) {

        }
    }
}

/**
 * The data class that will describe the state of the view
 */
@Keep
data class WineState(
    val firstDescription : String = "",
    val tours: List<WineTour> = emptyList(),
    val socials: List<WineSocial> = emptyList(),
    val error: String = "",
) {
}

@Keep
sealed class WinePartialState {
    data class OnWineTourSuccess(val data: List<WineTour>) : WinePartialState()
    object Loading : WinePartialState()
    data class OnWineSocialSuccess(val data: List<WineSocial>) : WinePartialState()
    data class Error(val message: String) : WinePartialState()

}

/**
 *  Here Intent is the same as Intent in MVI architecture
 *  Google likes to call it Event as Android already has an Intent class
 */
sealed class WineUiEvent {
}