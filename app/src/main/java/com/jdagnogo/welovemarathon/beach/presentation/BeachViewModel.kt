package com.jdagnogo.welovemarathon.beach.presentation

import androidx.annotation.Keep
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.beach.domain.BeachUseCases
import com.jdagnogo.welovemarathon.beach.domain.PrivateBeach
import com.jdagnogo.welovemarathon.common.ui.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.handleResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalPagerApi
@HiltViewModel
class BeachViewModel @Inject constructor(
    private val beachesUseCase: BeachUseCases,
    private val reducer: BeachReducer,
    val pagerState: PagerState,
) : ViewModel(), IModel<BeachState, BeachUiEvent> {
    private val _state = MutableStateFlow(BeachState())
    override val state: StateFlow<BeachState> get() = _state
    var shouldRedirect = true

    init {
        dispatchEvent(BeachUiEvent.FetchBeaches)
        viewModelScope.launch {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                dispatchEvent(
                    BeachUiEvent.FetchPrivatesBeaches(
                        state.value.beaches.getOrNull(page)?.id ?: ""
                    )
                )
            }
        }
    }

    fun setPage(currentPage: Int) {
        if (shouldRedirect){
            viewModelScope.launch {
                delay(1000)
                pagerState.scrollToPage(currentPage, 0f)
            }
        }
    }

    override fun dispatchEvent(event: BeachUiEvent) {
        when (event) {
            BeachUiEvent.FetchBeaches -> {
                fetchBeaches()
            }
            is BeachUiEvent.FetchPrivatesBeaches -> {
                fetchPrivateBeaches(parentId = event.parentId)
            }
            is BeachUiEvent.ScrollTo -> {
                setPage(event.page)
            }
        }
    }

    private fun fetchBeaches() {
        viewModelScope.launch {
            beachesUseCase.getBeachesUseCase.invoke().onEach { resource ->
                val partialState = when (resource) {
                    is Resource.Success -> {
                        BeachPartialState.OnBeachSuccess(resource.data ?: listOf())
                    }
                    else -> {
                        BeachPartialState.Error("")
                    }
                }
                _state.value = reducer.reduce(state.value, partialState)
            }.launchIn(this)
        }
    }

    private fun fetchPrivateBeaches(parentId: String) {
        viewModelScope.launch {
            handleResource(
                beachesUseCase.getPrivateBeachesUseCase.invoke(parentId = parentId),
                { BeachPartialState.OnPrivateBeachSuccess(it) },
                BeachPartialState.Loading,
                { BeachPartialState.Error("") },
                { _state.value = reducer.reduce(state.value, it) },
                this
            )
            shouldRedirect = false
        }
    }
}

@Keep
data class BeachState(
    val beaches: List<Beach> = listOf(),
    val privateBeaches: List<PrivateBeach> = listOf(),
    val error: String = "",
)

@Keep
sealed class BeachPartialState {
    object Loading : BeachPartialState()
    data class Error(val message: String) : BeachPartialState()
    data class OnBeachSuccess(val beaches: List<Beach>) : BeachPartialState()
    data class OnPrivateBeachSuccess(val privateBeaches: List<PrivateBeach>) : BeachPartialState()
}

@Keep
sealed class BeachUiEvent {
    object FetchBeaches : BeachUiEvent()
    data class FetchPrivatesBeaches(val parentId: String) : BeachUiEvent()
    data class ScrollTo(val page: Int) : BeachUiEvent()
}
