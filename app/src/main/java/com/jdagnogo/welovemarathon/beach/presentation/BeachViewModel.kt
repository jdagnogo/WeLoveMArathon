package com.jdagnogo.welovemarathon.beach.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import com.jdagnogo.welovemarathon.common.ui.IModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BeachViewModel @Inject constructor() : ViewModel(), IModel<BeachState, BeachUiEvent> {
    private val _state = MutableStateFlow(BeachState())
    override val state: StateFlow<BeachState> get() = _state

    override fun dispatchEvent(event: BeachUiEvent) {
       when(event){
           BeachUiEvent.FetchBeaches -> {}
       }
    }

}

@Keep
data class BeachState(
    val error: String = "",
)

@Keep
sealed class BeachUiEvent {
    object FetchBeaches : BeachUiEvent()
}
