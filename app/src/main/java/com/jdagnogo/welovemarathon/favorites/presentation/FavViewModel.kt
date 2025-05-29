package com.jdagnogo.welovemarathon.favorites.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.beach.presentation.BeachUiEvent
import com.jdagnogo.welovemarathon.common.like.domain.FavUseCase
import com.jdagnogo.welovemarathon.common.like.domain.Favorite
import com.jdagnogo.welovemarathon.common.utils.IModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FavViewModel @Inject constructor(
    private val useCases: FavUseCase,
    private val reducer: FavReducer,
) : ViewModel(), IModel<FavViewModel.FavState, FavViewModel.FavUiEvent> {

    private val _state = MutableStateFlow(FavState())
    override val state: StateFlow<FavState> get() = _state

    init {
        fetchFavs()
    }

    private fun fetchFavs() {
        viewModelScope.launch {
           useCases.getAllFavUseCases().onEach { favs ->
               val partialState  = FavPartialState.OnFavSuccess(favs)
               _state.value = reducer.reduce(state.value, partialState)
           }.launchIn(this)
        }
    }

    private fun clearAll(){
        viewModelScope.launch {
            useCases.deleteAllFavoriteUseCase()
        }
    }

    override fun dispatchEvent(event: FavUiEvent) {
        when (event) {
            FavUiEvent.OnClearAllClicked -> {
                clearAll()
            }
        }
    }

    /**
     * The data class that will describe the state of the view
     */
    @Keep
    data class FavState(
        val favorites: List<Favorite> = emptyList(),
    ){
        val hasFavorites = favorites.isNotEmpty()
    }

    @Keep
    sealed class FavPartialState {
        data class Error(val message: String) : FavPartialState()
        data class OnFavSuccess(val favorites: List<Favorite>) : FavPartialState()
    }

    @Keep
    sealed class FavUiEvent {
        object OnClearAllClicked : FavUiEvent()
    }

}