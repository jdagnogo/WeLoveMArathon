package com.jdagnogo.welovemarathon.home.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.common.banner.GifBanner
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.home.domain.Activities
import com.jdagnogo.welovemarathon.home.domain.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _state = MutableStateFlow(HomeState())
    override val state: StateFlow<HomeState> get() = _state

    init {
        dispatchEvent(HomeUiEvent.FetchBanner)
        dispatchEvent(HomeUiEvent.FetchBeaches)
    }

    override fun dispatchEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.FetchBeaches -> fetchBeaches()
            HomeUiEvent.FetchBanner -> fetchBanner()
        }
    }

    private fun fetchBeaches() {
        viewModelScope.launch {
            homeUseCases.getBeachesUseCase.invoke().onEach { resource ->
                val partialState = when (resource) {
                    is Resource.Success -> {
                        HomePartialState.OnBeachesSuccess(resource.data ?: listOf())
                    }
                    is Resource.Loading -> {
                        HomePartialState.LoadingBeaches
                    }
                    else -> {
                        HomePartialState.Error("")
                    }
                }
                _state.value = reducer.reduce(_state.value, partialState)
            }.launchIn(this)
        }
    }

    private fun fetchBanner() {
        viewModelScope.launch {
            homeUseCases.getHomeBannerUseCase.invoke().onEach { resource ->
                val partialState = when (resource) {
                    is Resource.Success -> {
                        HomePartialState.OnBannerSuccess(resource.data?.firstOrNull()
                            ?: GifBanner())
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
)

@Keep
sealed class HomePartialState {
    object LoadingBeaches : HomePartialState()
    data class OnBannerSuccess(val banner: GifBanner?) : HomePartialState()
    data class OnBeachesSuccess(val data: List<Beach>) : HomePartialState()
    data class Error(val message: String) : HomePartialState()
}

/**
 *  Here Intent is the same as Intent in MVI architecture
 *  Google likes to call it Event as Android already has an Intent class
 */
sealed class HomeUiEvent {
    object FetchBeaches : HomeUiEvent()
    object FetchBanner : HomeUiEvent()
}
