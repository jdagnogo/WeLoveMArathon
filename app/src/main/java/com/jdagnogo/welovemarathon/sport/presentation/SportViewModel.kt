package com.jdagnogo.welovemarathon.sport.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.ui.IModel
import com.jdagnogo.welovemarathon.common.utils.handleResource
import com.jdagnogo.welovemarathon.sport.domain.Sport
import com.jdagnogo.welovemarathon.sport.domain.SportCategory
import com.jdagnogo.welovemarathon.sport.domain.SportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportViewModel @Inject constructor(
    private val sportUseCase: SportUseCase,
    private val reducer: SportReducer,
) : ViewModel(), IModel<SportState, SportUiEvent> {

    private val _state = MutableStateFlow(SportState())
    override val state: StateFlow<SportState> get() = _state

    private var currentSelected: SportCategory? = null

    init {
        viewModelScope.launch {
            dispatchEvent(SportUiEvent.FetchCategories)
        }
    }

    override fun dispatchEvent(event: SportUiEvent) {
        val categories = state.value.categories
        when (event) {
            is SportUiEvent.OnCategorySelected -> {
                onCategorySelected(
                    categories.firstOrNull { it.name == event.type })
            }
            SportUiEvent.FetchCategories -> fetchCategories()
        }
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            handleResource(sportUseCase.getSportCategoriesUseCase.invoke(),
                { SportPartialState.OnCategoriesSuccess(it) },
                SportPartialState.Loading,
                { SportPartialState.Error("") },
                {
                    _state.value = reducer.reduce(state.value, it)
                    dispatchEvent(SportUiEvent.OnCategorySelected(state.value.categories.firstOrNull()?.name))
                },
                this)
        }
    }

    private fun fetchSports(type: SportCategory) {
        if (currentSelected == type) return
        currentSelected = type
        viewModelScope.launch {
            handleResource(sportUseCase.getSportsUseCase.invoke(type.name),
                { SportPartialState.OnSportsSuccess(it, type) },
                SportPartialState.Loading,
                { SportPartialState.Error("") },
                { _state.value = reducer.reduce(state.value, it) },
                this)
        }
    }

    private fun onCategorySelected(type: SportCategory?) {
        viewModelScope.launch {
            type?.let {
                fetchSports(type = type)
                currentSelected = type
            }
        }
    }
}

@Keep
data class SportState(
    val categories: List<SportCategory> = listOf(),
    val sports: List<Sport> = listOf(),
    val currentCategory: SportCategory? = SportCategory(),
    val error: String = "",
)

@Keep
sealed class SportPartialState {
    object Loading : SportPartialState()
    data class Error(val message: String) : SportPartialState()
    data class OnSportsSuccess(val data: List<Sport>, val currentSelected: SportCategory) :
        SportPartialState()

    data class OnCategoriesSuccess(val data: List<SportCategory>) : SportPartialState()
}

@Keep
sealed class SportUiEvent {
    data class OnCategorySelected(val type: String?) : SportUiEvent()
    object FetchCategories : SportUiEvent()
}
