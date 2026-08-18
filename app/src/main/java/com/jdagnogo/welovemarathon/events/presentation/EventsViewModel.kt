package com.jdagnogo.welovemarathon.events.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.events.domain.Event
import com.jdagnogo.welovemarathon.events.domain.GetEventsUseCase
import com.jdagnogo.welovemarathon.events.domain.RefreshEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val refreshEventsUseCase: RefreshEventsUseCase,
    private val reducer: EventsReducer,
) : ViewModel(), IModel<EventsState, EventsUiEvent> {

    private val _state = MutableStateFlow(EventsState())
    override val state: StateFlow<EventsState> get() = _state

    init {
        observeEvents()
    }

    private fun observeEvents() {
        getEventsUseCase().onEach { events ->
            val partialState = EventsPartialState.OnEventsSuccess(events)
            _state.value = reducer.reduce(_state.value, partialState)
        }.launchIn(viewModelScope)
    }

    // Firestore -> Room refresh, driven by EventsScreen's LaunchedEffect firing
    // every time this composable re-enters composition (i.e. every time the user
    // navigates into the Events tab, per Decision 11) - not by ViewModel init,
    // since bottom-nav tabs keep their ViewModel alive across tab switches, so
    // init only ever runs once per app session.
    private fun refresh() {
        viewModelScope.launch {
            _state.value = reducer.reduce(_state.value, EventsPartialState.Loading)
            runCatching { refreshEventsUseCase() }
                .onFailure {
                    _state.value = reducer.reduce(
                        _state.value,
                        EventsPartialState.Error(it.message ?: ""),
                    )
                }
        }
    }

    override fun dispatchEvent(event: EventsUiEvent) {
        when (event) {
            EventsUiEvent.OnScreenEntered -> refresh()
            is EventsUiEvent.OnSearchQueryChanged -> {
                _state.value =
                    reducer.reduce(state.value, EventsPartialState.OnSearchQueryChanged(event.query))
            }
            is EventsUiEvent.OnCategorySelected -> {
                _state.value =
                    reducer.reduce(state.value, EventsPartialState.OnCategorySelected(event.category))
            }
            EventsUiEvent.OnAllCategoriesSelected -> {
                _state.value = reducer.reduce(state.value, EventsPartialState.OnAllCategoriesSelected)
            }
        }
    }
}

/**
 * The data class that will describe the state of the view
 */
@Keep
data class EventsState(
    val events: List<Event> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val isLoading: Boolean = false,
    val error: String = "",
) {
    private val filteredEvents: List<Event>
        get() = events.filter { event ->
            val matchesQuery = searchQuery.isBlank() || listOf(
                event.title,
                event.description,
                event.location,
            ).any { it.contains(searchQuery, ignoreCase = true) }
            val matchesCategory = selectedCategory == null || event.category == selectedCategory
            matchesQuery && matchesCategory
        }

    val groupedEvents: Map<LocalDate, List<Event>>
        get() = groupEventsByDay(filteredEvents)
}

@Keep
sealed class EventsPartialState {
    object Loading : EventsPartialState()
    data class Error(val message: String) : EventsPartialState()
    data class OnEventsSuccess(val data: List<Event>) : EventsPartialState()
    data class OnSearchQueryChanged(val query: String) : EventsPartialState()
    data class OnCategorySelected(val category: String) : EventsPartialState()
    object OnAllCategoriesSelected : EventsPartialState()
}

/**
 *  Here Intent is the same as Intent in MVI architecture
 *  Google likes to call it Event as Android already has an Intent class
 */
@Keep
sealed class EventsUiEvent {
    object OnScreenEntered : EventsUiEvent()
    data class OnSearchQueryChanged(val query: String) : EventsUiEvent()
    data class OnCategorySelected(val category: String) : EventsUiEvent()
    object OnAllCategoriesSelected : EventsUiEvent()
}
