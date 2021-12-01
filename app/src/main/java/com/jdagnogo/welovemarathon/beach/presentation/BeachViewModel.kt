package com.jdagnogo.welovemarathon.beach.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.beach.domain.GetBeachesUseCase
import com.jdagnogo.welovemarathon.common.ui.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeachViewModel @Inject constructor(
    private val beachesUseCase: GetBeachesUseCase,
    private val reducer: BeachReducer,
) : ViewModel(), IModel<BeachState, BeachUiEvent> {
    private val _state = MutableStateFlow(BeachState())
    override val state: StateFlow<BeachState> get() = _state

    init {
        dispatchEvent(BeachUiEvent.FetchBeaches)
    }

    override fun dispatchEvent(event: BeachUiEvent) {
        when (event) {
            BeachUiEvent.FetchBeaches -> {
                fetchBeaches()
            }
            BeachUiEvent.FetchPrivatesBeaches -> TODO()
        }
    }

    private fun fetchBeaches() {
        viewModelScope.launch {
            beachesUseCase.invoke().onEach { resource ->
                val partialState = when (resource) {
                    is Resource.Success -> {
                        BeachPartialState.OnBeachSuccess(resource.data ?: listOf())
                    }
                    else -> {
                        BeachPartialState.Error("")
                    }
                }
                _state.value = reducer.reduce(_state.value, partialState)
            }.launchIn(this)
        }
    }
}

@Keep
data class BeachState(
    val beaches: List<Beach> = listOf(),
    val error: String = "",
)

@Keep
sealed class BeachPartialState {
    data class Error(val message: String) : BeachPartialState()
    data class OnBeachSuccess(val beaches: List<Beach>) : BeachPartialState()
}

@Keep
sealed class BeachUiEvent {
    object FetchBeaches : BeachUiEvent()
    object FetchPrivatesBeaches : BeachUiEvent()
}
