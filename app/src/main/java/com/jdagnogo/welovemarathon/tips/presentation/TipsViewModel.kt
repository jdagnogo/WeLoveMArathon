package com.jdagnogo.welovemarathon.tips.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.home.domain.Activities
import com.jdagnogo.welovemarathon.tips.domain.GetTipsUseCase
import com.jdagnogo.welovemarathon.tips.domain.Tips
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipsViewModel @Inject constructor(
    private val useCases: GetTipsUseCase,
    private val reducer: TipsReducer,
) : ViewModel(), IModel<TipsState, TipsUiEvent> {

    private val _state = MutableStateFlow(TipsState())
    override val state: StateFlow<TipsState> get() = _state

    init {
        viewModelScope.launch {
            dispatchEvent(TipsUiEvent.FetchTips)
        }
    }

    private fun fetchTips() {
        viewModelScope.launch {
            useCases.invoke().onEach { resource ->
                val partialState = when (resource) {
                    is Resource.Success -> {
                        TipsPartialState.OnTipsSuccess(resource.data ?: listOf())
                    }
                    else -> {
                        TipsPartialState.Error("")
                    }
                }
                _state.value = reducer.reduce(_state.value, partialState)
            }.launchIn(this)
        }
    }

    override fun dispatchEvent(event: TipsUiEvent) {
        when (event) {
            is TipsUiEvent.FetchTips -> fetchTips()
            is TipsUiEvent.OnTipSelected -> {
                val tips = state.value.tips.firstOrNull { it.ordinal == event.position }
                val partialState = TipsPartialState.OnTipSelected(tips)
                _state.value = reducer.reduce(_state.value, partialState)
            }
            TipsUiEvent.OnCloseDialog -> {
                val partialState = TipsPartialState.OnCloseDialog
                _state.value = reducer.reduce(_state.value, partialState)
            }
        }
    }
}

/**
 * The data class that will describe the state of the view
 */
@Keep
data class TipsState(
    val currentSelected: Tips? = null,
    val tips: List<Tips> = listOf(),
    val error: String = "",
) {
    val shouldOpenDialog: Boolean = currentSelected != null
}

@Keep
sealed class TipsPartialState {
    object OnCloseDialog : TipsPartialState()
    data class Error(val message: String) : TipsPartialState()
    data class OnTipSelected(val tips: Tips?) : TipsPartialState()
    data class OnTipsSuccess(val data: List<Tips>) : TipsPartialState()
}

/**
 *  Here Intent is the same as Intent in MVI architecture
 *  Google likes to call it Event as Android already has an Intent class
 */
sealed class TipsUiEvent {
    object FetchTips : TipsUiEvent()
    object OnCloseDialog : TipsUiEvent()
    data class OnTipSelected(val position: Int) : TipsUiEvent()
}
