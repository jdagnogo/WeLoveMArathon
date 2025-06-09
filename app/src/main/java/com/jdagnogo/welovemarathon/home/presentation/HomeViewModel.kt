package com.jdagnogo.welovemarathon.home.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.common.banner.GifBanner
import com.jdagnogo.welovemarathon.common.banner.HOME
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.home.domain.Activities
import com.jdagnogo.welovemarathon.home.domain.HomeUseCases
import com.jdagnogo.welovemarathon.offer.domain.OfferWithRestaurant
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantAppliedFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val reducer: HomeReducer,
) : ViewModel(),
    IModel<HomeState, HomeUiEvent> {
    private val COUNT_MAX = 2
    private val _state = MutableStateFlow(HomeState())
    override val state: StateFlow<HomeState> get() = _state

    init {
        dispatchEvent(HomeUiEvent.FetchBanner)
        dispatchEvent(HomeUiEvent.FetchBeaches)
        fetchOffers()
    }

    override fun dispatchEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.FetchBeaches -> fetchBeaches()
            HomeUiEvent.FetchBanner -> fetchBanner()
            is HomeUiEvent.OnOfferDisplayed -> onOfferDisplayed(event.offerId)
        }
    }

    private fun onOfferDisplayed(offerId: String) {
        viewModelScope.launch {
            homeUseCases.updateOfferDisplayCountUseCase.invoke(offerId)
        }
    }

    private fun fetchBeaches() {
        viewModelScope.launch {
            homeUseCases.getRestaurantUseCase.invoke(RestaurantAppliedFilter())
        }
    }

    private fun fetchOffers() {
        viewModelScope.launch {
            homeUseCases.getOfferUseCase().onEach { resource ->
                val offer = resource.data
                val count = homeUseCases.getOfferCountUseCase(offer?.id)
                if (resource is Resource.Success && offer != null) {
                    val shouldShowPopup = count == 0
                    val partialState = HomePartialState.OnOfferSuccess(offer, shouldShowPopup)
                    _state.value = reducer.reduce(state.value, partialState)
                    this.cancel()
                }
            }.launchIn(this)
        }
    }

    private fun fetchBanner() {
        viewModelScope.launch {
            homeUseCases.getBannerUseCase.invoke(HOME).onEach { resource ->
                val partialState = when (resource) {
                    is Resource.Success -> {
                        HomePartialState.OnBannerSuccess(
                            resource.data?.firstOrNull()
                                ?: GifBanner()
                        )
                    }

                    is Resource.Loading -> {
                        HomePartialState.LoadingBeaches
                    }

                    else -> {
                        HomePartialState.Error(resource.message ?: "")
                    }
                }
                _state.value = reducer.reduce(_state.value, partialState)
            }.launchIn(this)
        }
    }
}

/**
 * The data class that will describe the state of the view
 */
@Keep
data class HomeState(
    val activities: List<Activities> = Activities.values().toList(),
    val beaches: List<Beach> = listOf(),
    val isLoadingBeaches: Boolean = true,
    val banner: GifBanner? = null,
    val hasError: Boolean = false,
    val shouldOpenOfferBottomSheet: Boolean = false,
    val offer: OfferWithRestaurant = OfferWithRestaurant(),
)

@Keep
sealed class HomePartialState {
    object LoadingBeaches : HomePartialState()
    data class OnBannerSuccess(val banner: GifBanner?) : HomePartialState()
    data class OnBeachesSuccess(val data: List<Beach>) : HomePartialState()
    data class Error(val message: String) : HomePartialState()
    data class OnOfferSuccess(
        val offer: OfferWithRestaurant,
        val shouldOpenOfferBottomSheet: Boolean
    ) : HomePartialState()
}

/**
 *  Here Intent is the same as Intent in MVI architecture
 *  Google likes to call it Event as Android already has an Intent class
 */
sealed class HomeUiEvent {
    object FetchBeaches : HomeUiEvent()
    object FetchBanner : HomeUiEvent()
    data class OnOfferDisplayed(val offerId: String) : HomeUiEvent()
}
