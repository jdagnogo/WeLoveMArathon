package com.jdagnogo.welovemarathon.events.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.events.domain.Event
import com.jdagnogo.welovemarathon.events.domain.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Deliberately not a full IModel/IReducer setup - this screen has no user
 * interaction to reduce, just "look up the one event this route was opened
 * for." Reuses the same Room-backed events flow the Events list already
 * observes rather than adding a second, separate read path.
 */
@HiltViewModel
class EventDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getEventsUseCase: GetEventsUseCase,
) : ViewModel() {

    private val eventId: String = checkNotNull(savedStateHandle[KEY_EVENT_ID])

    val event: StateFlow<Event?> = getEventsUseCase()
        .map { events -> events.firstOrNull { it.id == eventId } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    companion object {
        const val KEY_EVENT_ID = "eventId"
    }
}
