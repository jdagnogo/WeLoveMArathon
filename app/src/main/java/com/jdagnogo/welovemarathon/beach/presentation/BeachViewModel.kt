package com.jdagnogo.welovemarathon.beach.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.beach.domain.BeachBar
import com.jdagnogo.welovemarathon.beach.domain.BeachUseCases
import com.jdagnogo.welovemarathon.common.banner.GifBanner
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.CategoryTag
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.handleResource
import com.jdagnogo.welovemarathon.common.utils.handleResourceWithFav
import com.jdagnogo.welovemarathon.food.domain.FoodTag
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingUiEvent
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

    companion object {
        const val BEACH_BAR_TAG = "Beach bar"
    }

    override fun dispatchEvent(event: BeachUiEvent) {
        when (event) {
            is BeachUiEvent.OnBeachSelected -> {
                fetchTags()
                fetchBeachesBar(event.parentId)
            }
            is BeachUiEvent.OnRecommendedItemSelected -> {
                val item = state.value.recommendedItems.firstOrNull { it.id == event.id }
                val partialState = BeachPartialState.OnRecommendedDialog(item = item)
                _state.value = reducer.reduce(state.value, partialState)
            }
            BeachUiEvent.OnDismissFilterRequest -> {
                val partialState = BeachPartialState.OnFilterDialog(false)
                _state.value = reducer.reduce(state.value, partialState)
            }
            is BeachUiEvent.OnFilterClicked -> {
                val partialState = BeachPartialState.OnFilterDialog(event.isVisible)
                _state.value = reducer.reduce(state.value, partialState)
            }
            is BeachUiEvent.OnFiltersSelected -> {
                fetchBeachesBar(
                    state.value.currentSelected.id,
                    event.filters
                )
                val partialState = BeachPartialState.OnFiltersSelected(
                    event.filters.map { FoodTag(it) }
                )
                _state.value = reducer.reduce(state.value, partialState)
            }
            BeachUiEvent.OnResetClicked -> {
                fetchBeachesBar(
                    state.value.currentSelected.id,
                    emptyList()
                )
                val partialState = BeachPartialState.OnFiltersSelected(emptyList())
                _state.value = reducer.reduce(state.value, partialState)
            }
            BeachUiEvent.OnRecommendedDialogClosed -> {
                val partialState = BeachPartialState.OnRecommendedDialog(item = null)
                _state.value = reducer.reduce(state.value, partialState)
            }
            is BeachUiEvent.OnLikeClicked -> {
                viewModelScope.launch {
                    val activities = state.value.categories.firstOrNull {
                        it.id == event.id
                    }
                    beachesUseCase.favUseCase.updateFavUseCase(activities)
                }
            }
        }
    }

    private fun fetchTags() {
        viewModelScope.launch {
            handleResource(
                beachesUseCase.getFoodTagUseCase.invoke(category = BEACH_BAR_TAG),
                { BeachPartialState.OnTagSuccess(it) },
                BeachPartialState.Loading,
                { BeachPartialState.Error("") },
                {
                    _state.value = reducer.reduce(state.value, it)
                },
                this
            )
        }
    }

    private fun fetchBeachesBar(parentId: String, tags: List<String> = emptyList()) {
        viewModelScope.launch {
            val beach =
                state.value.beaches.firstOrNull { it.id == parentId }
                    ?: state.value.beaches.first()

            handleResourceWithFav(
                beachesUseCase.getBeachesBarUseCase.invoke(beach.name, tags),
                beachesUseCase.favUseCase.getAllFavUseCases(),
                { beaches, favorites ->
                    BeachPartialState.OnBeachesBarsSuccess(
                        categories = beaches.map { it.toCategoryItem(favorites.firstOrNull { fav -> fav.id == it.id } != null) },
                        currentSelected = beach,
                        shouldDisplayFilter = shouldDisplayFilter(tags, beaches),
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

    private fun shouldDisplayFilter(tags: List<String>, beaches: List<BeachBar>): Boolean {
        return if (tags.isEmpty()) {
            beaches.size > 1
        } else {
            true
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
    val mainScreenName: String = "Beaches",
    val currentSelected: Beach = Beach(),
    val categories: List<CategoryItem> = listOf(),
    val beaches: List<Beach> = listOf(),
    val currentBeachSelected: RecommendedCategoryDetails? = null,
    val recommendedItems: List<RecommendedCategoryDetails> = emptyList(),
    val shouldOpenRecommendedDialog: Boolean = false,
    val shouldOpenFilterDialog: Boolean = false,
    val tags: List<FoodTag> = listOf(),
    val currentSelectedTags: List<FoodTag> = listOf(),
    val banner: GifBanner? = null,
    val error: String = "",
    val shouldDisplayFilter: Boolean = false,
) {
    val longCategoryItems = beaches.map { it.toLongCategoryItem() }
    val typeTwoItem = currentSelected.toTypeTwoItem()
    val categoryTags: List<CategoryTag> = tags.map { tag ->
        CategoryTag(
            name = tag.name,
            isSelected = currentSelectedTags.firstOrNull { it.name == tag.name } != null
        )
    }
}

@Keep
sealed class BeachPartialState {
    object Loading : BeachPartialState()
    data class OnRecommendedDialog(val item: RecommendedCategoryDetails?) : BeachPartialState()
    data class Error(val message: String) : BeachPartialState()
    data class OnBeachSuccess(val beaches: List<Beach>) : BeachPartialState()
    data class OnTagSuccess(val data: List<FoodTag>) : BeachPartialState()
    data class OnBeachesBarsSuccess(
        val categories: List<CategoryItem>,
        val currentSelected: Beach,
        val shouldDisplayFilter: Boolean,
    ) : BeachPartialState()

    data class OnFilterDialog(val isVisible: Boolean) : BeachPartialState()
    data class OnFiltersSelected(val data: List<FoodTag>) : BeachPartialState()
    data class OnBeachesBarsRecommendedSuccess(val recommendedItems: List<RecommendedCategoryDetails>) :
        BeachPartialState()
}

@Keep
sealed class BeachUiEvent {
    data class OnBeachSelected(val parentId: String) : BeachUiEvent()
    data class OnRecommendedItemSelected(val id: String) : BeachUiEvent()
    object OnResetClicked : BeachUiEvent()
    data class OnLikeClicked(val id: String) : BeachUiEvent()
    object OnDismissFilterRequest : BeachUiEvent()
    data class OnFilterClicked(val isVisible: Boolean) : BeachUiEvent()
    data class OnFiltersSelected(val filters: List<String>) : BeachUiEvent()
    object OnRecommendedDialogClosed : BeachUiEvent()
}
