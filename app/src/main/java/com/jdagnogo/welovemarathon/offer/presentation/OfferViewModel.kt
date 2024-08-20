package com.jdagnogo.welovemarathon.offer.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.offer.domain.OfferUseCase
import com.jdagnogo.welovemarathon.offer.domain.OfferWithRestaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val useCases: OfferUseCase,
    private val reducer: OfferReducer,
) : ViewModel(), IModel<OfferViewModel.OfferState, OfferViewModel.OfferUiEvent> {

    private val _state = MutableStateFlow(OfferState())
    override val state: StateFlow<OfferState> get() = _state

    init {
        fetchOffers()
    }

    private fun fetchOffers() {
        viewModelScope.launch {
            useCases.getOfferUseCase().onEach { resource ->
                val offer = resource.data
                if (resource is Resource.Success && offer != null) {
                    val partialState = OfferPartialState.OnOfferSuccess(offer)
                    _state.value = reducer.reduce(state.value, partialState)
                }
            }.launchIn(this)
        }
    }

    override fun dispatchEvent(event: OfferUiEvent) {
        when (event) {
            OfferUiEvent.OnBookNow -> {

            }
        }
    }

    /**
     * The data class that will describe the state of the view
     */
    @Keep
    data class OfferState(
        val offer: OfferWithRestaurant = OfferWithRestaurant(),
    )

    @Keep
    sealed class OfferPartialState {
        data object Empty : OfferPartialState()
        data class OnOfferSuccess(val offer: OfferWithRestaurant) : OfferPartialState()
    }

    @Keep
    sealed class OfferUiEvent {
        object OnBookNow : OfferUiEvent()
    }

}