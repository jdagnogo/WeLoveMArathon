package com.jdagnogo.welovemarathon.events.presentation

import com.jdagnogo.welovemarathon.common.utils.IReducer

class NotificationSettingsReducer :
    IReducer<NotificationSettingsState, NotificationSettingsPartialState> {
    override fun reduce(
        state: NotificationSettingsState,
        partialState: NotificationSettingsPartialState,
    ): NotificationSettingsState {
        return when (partialState) {
            is NotificationSettingsPartialState.OnNewEventsToggled ->
                state.copy(newEventsEnabled = partialState.enabled)
            is NotificationSettingsPartialState.OnCancellationsToggled ->
                state.copy(cancellationsEnabled = partialState.enabled)
        }
    }
}
