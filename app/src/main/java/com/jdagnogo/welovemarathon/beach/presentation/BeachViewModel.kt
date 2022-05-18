package com.jdagnogo.welovemarathon.beach.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.beach.domain.BeachUseCases
import com.jdagnogo.welovemarathon.common.banner.GifBanner
import com.jdagnogo.welovemarathon.common.category.CategoryItem
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
        fetchRecommendedBeachesBars()
    }

    override fun dispatchEvent(event: BeachUiEvent) {
        when (event) {
            is BeachUiEvent.OnBeachSelected -> {
                fetchBeachesBar(event.parentId)
            }
        }
    }

    private fun fetchBeachesBar(parentId: String) {
        viewModelScope.launch {
            val beach =
                state.value.beaches.firstOrNull { it.id == parentId }
                    ?: state.value.beaches.first()
            handleResource(
                beachesUseCase.getBeachesBarUseCase.invoke(beach.name),
                { beaches ->
                    BeachPartialState.OnBeachesBarsSuccess(
                        categories = beaches.map { it.toCategoryItem() },
                        currentSelected = beach,
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

    private fun fetchRecommendedBeachesBars() {
        viewModelScope.launch {
            handleResource(
                beachesUseCase.getRecommendedBeachesBarsUseCase.invoke(),
                { beaches ->
                    BeachPartialState.OnBeachesBarsRecommendedSuccess(
                        recommendedItems = beaches.map { it.toRecommendedCategoryItem() },
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

    private fun fetchBeaches() {
        viewModelScope.launch {
            handleResource(
                beachesUseCase.getBeachesUseCase.invoke(),
                { beaches ->
                    BeachPartialState.OnBeachSuccess(
                        beaches = beaches,
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

@Keep
data class BeachState(
    val currentSelected: Beach = Beach(name = "Beaches"),
    val categories: List<CategoryItem> = listOf(),
    val beaches: List<Beach> = listOf(),
    val currentBeachSelected: RecommendedCategoryDetails? = null,
    val recommendedItems: List<RecommendedCategoryDetails> = emptyList(),
    val shouldOpenRecommendedDialog: Boolean = false,
    val banner: GifBanner? = null,
    val error: String = "",
) {
    val longCategoryItems = beaches.map { it.toLongCategoryItem() }
    val typeTwoItem = currentSelected.toTypeTwoItem()
}

@Keep
sealed class BeachPartialState {
    object Loading : BeachPartialState()
    data class Error(val message: String) : BeachPartialState()
    data class OnBeachSuccess(val beaches: List<Beach>) : BeachPartialState()
    data class OnBeachesBarsSuccess(
        val categories: List<CategoryItem>,
        val currentSelected: Beach
    ) : BeachPartialState()

    data class OnBeachesBarsRecommendedSuccess(val recommendedItems: List<RecommendedCategoryDetails>) :
        BeachPartialState()
}

@Keep
sealed class BeachUiEvent {
    data class OnBeachSelected(val parentId: String) : BeachUiEvent()
}
