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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import java.util.Calendar
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
            combine(
                useCases.getOfferUseCase(),
                useCases.getOfferActivatedUseCase()
            ) { resource, offerActivated ->
                val offer = resource.data
                val activationDate =
                    offerActivated.data?.find { it.id == offer?.id }?.activationDate
                if (resource is Resource.Success) {
                    val partialState =
                        OfferPartialState.OnOfferSuccess(offer, activationDate = activationDate)
                    _state.value = reducer.reduce(state.value, partialState)
                }
            }.launchIn(this)
        }
    }

    override fun dispatchEvent(event: OfferUiEvent) {
        when (event) {
            is OfferUiEvent.OnBookNow -> onBookNow(event.offerId)
        }
    }

    private fun onBookNow(offerId: String) {
        viewModelScope.launch {
            useCases.activateOfferUseCase(offerId, Calendar.getInstance().time)
        }
    }

    /**
     * The data class that will describe the state of the view
     */
    @Keep
    data class OfferState(
        val offer: OfferWithRestaurant? = null,
        val activationDate: String? = null
    )

    @Keep
    sealed class OfferPartialState {
        data object Empty : OfferPartialState()
        data class OnOfferSuccess(
            val offer: OfferWithRestaurant?,
            val activationDate: String?
        ) : OfferPartialState()
    }

    @Keep
    sealed class OfferUiEvent {
        data class OnBookNow(val offerId: String) : OfferUiEvent()
    }

}