package com.jdagnogo.welovemarathon.home.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.banner.GifBanner
import com.jdagnogo.welovemarathon.common.ui.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
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
    private val runUseCases: HomeUseCases,
    private val reducer: HomeReducer,
) : ViewModel(),
    IModel<HomeState, HomeUiEvent> {
    private val _state = MutableStateFlow(HomeState())
    override val state: StateFlow<HomeState> get() = _state

    init {
        dispatchEvent(HomeUiEvent.FetchBanner)
    }

    override fun dispatchEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.FetchBeaches -> {
            }
            HomeUiEvent.FetchBanner -> fetchBanner()
        }
    }

    private fun fetchBanner() {
        viewModelScope.launch {
            runUseCases.getHomeBannerUseCase.invoke().onEach { resource ->
                val partialState = when (resource) {
                    is Resource.Success -> {
                        HomePartialState.OnBannerSuccess(resource.data?.first())
                    }
                    else -> {
                        HomePartialState.Error("")
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
    val isLoadingBeaches: Boolean = true,
    val banner: GifBanner? = null,
)

@Keep
sealed class HomePartialState {
    object LoadingBeaches : HomePartialState()
    data class OnBannerSuccess(val banner: GifBanner?) : HomePartialState()
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
