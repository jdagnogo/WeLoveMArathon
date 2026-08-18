package com.jdagnogo.welovemarathon.events.presentation

import android.content.Context
import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.utils.IModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationSettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val reducer: NotificationSettingsReducer,
) : ViewModel(), IModel<NotificationSettingsState, NotificationSettingsUiEvent> {

    private val _state = MutableStateFlow(
        NotificationSettingsState(
            newEventsEnabled = EventNotificationPreferences.isNewEventsEnabled(context),
            cancellationsEnabled = EventNotificationPreferences.isCancellationsEnabled(context),
        ),
    )
    override val state: StateFlow<NotificationSettingsState> get() = _state

    override fun dispatchEvent(event: NotificationSettingsUiEvent) {
        when (event) {
            is NotificationSettingsUiEvent.OnNewEventsToggled -> {
                EventNotificationPreferences.setNewEventsEnabled(context, event.enabled)
                applyChange(NotificationSettingsPartialState.OnNewEventsToggled(event.enabled))
            }
            is NotificationSettingsUiEvent.OnCancellationsToggled -> {
                EventNotificationPreferences.setCancellationsEnabled(context, event.enabled)
                applyChange(NotificationSettingsPartialState.OnCancellationsToggled(event.enabled))
            }
        }
    }

    private fun applyChange(partialState: NotificationSettingsPartialState) {
        viewModelScope.launch { EventNotificationPreferences.syncSubscriptions(context) }
        _state.value = reducer.reduce(state.value, partialState)
    }
}

@Keep
data class NotificationSettingsState(
    val newEventsEnabled: Boolean = true,
    val cancellationsEnabled: Boolean = true,
)

@Keep
sealed class NotificationSettingsPartialState {
    data class OnNewEventsToggled(val enabled: Boolean) : NotificationSettingsPartialState()
    data class OnCancellationsToggled(val enabled: Boolean) : NotificationSettingsPartialState()
}

@Keep
sealed class NotificationSettingsUiEvent {
    data class OnNewEventsToggled(val enabled: Boolean) : NotificationSettingsUiEvent()
    data class OnCancellationsToggled(val enabled: Boolean) : NotificationSettingsUiEvent()
}
