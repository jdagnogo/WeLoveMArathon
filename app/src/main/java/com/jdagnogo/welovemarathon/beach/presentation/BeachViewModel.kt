package com.jdagnogo.welovemarathon.beach.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.beach.domain.BeachCategory
import com.jdagnogo.welovemarathon.beach.domain.BeachUseCases
import com.jdagnogo.welovemarathon.beach.domain.PrivateBeach
import com.jdagnogo.welovemarathon.common.banner.GifBanner
import com.jdagnogo.welovemarathon.common.category.LongCategoryItem
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.handleResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalPagerApi
@HiltViewModel
class BeachViewModel @Inject constructor(
    private val beachesUseCase: BeachUseCases,
    private val reducer: BeachReducer,
) : ViewModel(), IModel<BeachState, BeachUiEvent> {
    private val _state = MutableStateFlow(BeachState())
    override val state: StateFlow<BeachState> get() = _state

    init {
        fetchBeaches()
    }

    override fun dispatchEvent(event: BeachUiEvent) {
        when (event) {
            BeachUiEvent.FetchBeaches -> {
                fetchBeaches()
            }
            is BeachUiEvent.FetchPrivatesBeaches -> {
                fetchPrivateBeaches(parentId = event.parentId)
            }
        }
    }

    private fun fetchBeaches() {
        viewModelScope.launch {
            viewModelScope.launch {
                handleResource(
                    beachesUseCase.getBeachesUseCase.invoke(),
                    { beaches ->
                        BeachPartialState.OnBeachSuccess(
                            beaches = beaches.map { it.toLongCategoryItem() },
                        )
                    },
                    BeachPartialState.Loading,
                    { BeachPartialState.Error("") },
                    { partialState ->
                        _state.value = reducer.reduce(state.value, partialState)
                    },
                    this
                )
            }
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
        }
    }
}

@Keep
data class BeachState(
    val currentSelected: BeachCategory = BeachCategory(),
    val categories: List<BeachCategory> = listOf(),
    val beaches: List<LongCategoryItem> = listOf(),
    val currentBeachSelected: RecommendedCategoryDetails? = null,
    val recommendedItems: List<RecommendedCategoryDetails> = emptyList(),
    val shouldOpenRecommendedDialog: Boolean = false,
    val banner: GifBanner? = null,
    val error: String = "",
)

@Keep
sealed class BeachPartialState {
    object Loading : BeachPartialState()
    data class Error(val message: String) : BeachPartialState()
    data class OnBeachSuccess(val beaches: List<LongCategoryItem>) : BeachPartialState()
    data class OnPrivateBeachSuccess(val privateBeaches: List<PrivateBeach>) : BeachPartialState()
}

@Keep
sealed class BeachUiEvent {
    object FetchBeaches : BeachUiEvent()
    data class FetchPrivatesBeaches(val parentId: String) : BeachUiEvent()
}
