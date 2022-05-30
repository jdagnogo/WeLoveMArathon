package com.jdagnogo.welovemarathon.activities.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.activities.domain.ActivitiesCategory
import com.jdagnogo.welovemarathon.activities.domain.ActivitiesTag
import com.jdagnogo.welovemarathon.activities.domain.ActivitiesUseCase
import com.jdagnogo.welovemarathon.common.banner.GifBanner
import com.jdagnogo.welovemarathon.common.banner.SHOPPING
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.CategoryTag
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails
import com.jdagnogo.welovemarathon.common.submenu.SubMenuUiModel
import com.jdagnogo.welovemarathon.common.ui.theme.ActivityColor
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.handleResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivitiesViewModel @Inject constructor(
    private val useCases: ActivitiesUseCase,
    private val reducer: ActivitiesReducer,
) : ViewModel(), IModel<ActivitiesState, ActivitiesUiEvent> {

    private val _state = MutableStateFlow(ActivitiesState())
    override val state: StateFlow<ActivitiesState> get() = _state

    init {
        fetchCategories()
        fetchBanner()
    }

    private var currentSelected: ActivitiesCategory? = null

    private fun fetchCategories() {
        viewModelScope.launch {
            handleResource(
                useCases.getActivitiesCategoriesUseCase.invoke(),
                { ActivitiesPartialState.OnCategoriesSuccess(it) },
                ActivitiesPartialState.Loading,
                { ActivitiesPartialState.Error("") },
                {
                    _state.value = reducer.reduce(state.value, it)
                },
                this
            )
        }
    }

    private fun fetchTags(category: String) {
        viewModelScope.launch {
            handleResource(
                useCases.getActivitiesTagUseCase.invoke(category = category),
                { ActivitiesPartialState.OnTagSuccess(it) },
                ActivitiesPartialState.Loading,
                { ActivitiesPartialState.Error("") },
                {
                    _state.value = reducer.reduce(state.value, it)
                },
                this
            )
        }
    }

    private fun fetchBanner() {
        viewModelScope.launch {
            useCases.getBannerUseCase.invoke(SHOPPING).onEach { resource ->
                val partialState = when (resource) {
                    is Resource.Success -> {
                        ActivitiesPartialState.OnBannerSuccess(
                            resource.data?.firstOrNull()
                                ?: GifBanner()
                        )
                    }
                    is Resource.Loading -> {
                        ActivitiesPartialState.Loading
                    }
                    else -> {
                        ActivitiesPartialState.Error(resource.message ?: "")
                    }
                }
                _state.value = reducer.reduce(_state.value, partialState)
            }.launchIn(this)
        }
    }

    private fun fetchActivities(category: ActivitiesCategory, tags: List<String> = emptyList()) {
        viewModelScope.launch {
            currentSelected = category
            viewModelScope.launch {
                handleResource(
                    useCases.getActivitiesUseCase.invoke(category.name, tags),
                    {
                        val activities = it.filter { it.isRecommended.not() }
                            .map { it.toCategoryItem() }
                        ActivitiesPartialState.OnActivitiessSuccess(
                            items = activities,
                            recommendedItems = it.filter { it.isRecommended }
                                .map { it.toRecommendedCategoryItem() },
                            shouldDisplayFilter = activities.size > 1
                        )
                    },
                    ActivitiesPartialState.Loading,
                    { ActivitiesPartialState.Error("") },
                    { partialState ->
                        _state.value = reducer.reduce(state.value, partialState)
                    },
                    this
                )
            }
        }
    }

    override fun dispatchEvent(event: ActivitiesUiEvent) {
        when (event) {
            is ActivitiesUiEvent.OnCategoryClicked -> {
                val category = state.value.categories[event.position]
                fetchTags(category = category.name)
                fetchActivities(category)
                val partialState = ActivitiesPartialState.OnCategoriesSelected(category)
                _state.value = reducer.reduce(state.value, partialState)
            }
            is ActivitiesUiEvent.OnFilterClicked -> {
                val partialState = ActivitiesPartialState.OnFilterDialog(event.isVisible)
                _state.value = reducer.reduce(state.value, partialState)
            }
            is ActivitiesUiEvent.OnRecommendedItemSelected -> {
                val item = state.value.recommendedItems.firstOrNull { it.id == event.id }
                val partialState = ActivitiesPartialState.OnRecommendedDialog(item = item)
                _state.value = reducer.reduce(state.value, partialState)
            }
            ActivitiesUiEvent.OnRecommendedDialogClosed -> {
                val partialState = ActivitiesPartialState.OnRecommendedDialog(item = null)
                _state.value = reducer.reduce(state.value, partialState)
            }
            is ActivitiesUiEvent.OnFiltersSelected -> {
                fetchActivities(
                    state.value.currentSelected,
                    event.filters
                )
                val partialState = ActivitiesPartialState.OnFiltersSelected(
                    event.filters.map { ActivitiesTag(it) }
                )
                _state.value = reducer.reduce(state.value, partialState)
            }
            ActivitiesUiEvent.OnResetClicked -> {
                fetchActivities(
                    state.value.currentSelected,
                    emptyList()
                )
                val partialState = ActivitiesPartialState.OnFilterReset
                _state.value = reducer.reduce(state.value, partialState)
            }
        }
    }
}

/**
 * The data class that will describe the state of the view
 */
@Keep
data class ActivitiesState(
    val currentSelected: ActivitiesCategory = ActivitiesCategory(),
    val categories: List<ActivitiesCategory> = listOf(),
    val currentSelectedTags: List<ActivitiesTag> = listOf(),
    val tags: List<ActivitiesTag> = listOf(),
    val activitiess: List<CategoryItem> = listOf(),
    val shouldDisplayFilter: Boolean = false,
    val currentActivitiesSelected: RecommendedCategoryDetails? = null,
    val recommendedItems: List<RecommendedCategoryDetails> = emptyList(),
    val shouldOpenRecommendedDialog: Boolean = false,
    val shouldOpenFilterDialog: Boolean = false,
    val banner: GifBanner? = null,
    val error: String = "",
) {
    val subMenuUiModel = SubMenuUiModel(
        screenName = "Activities",
        items = categories.map { it.toSubMenuItem() },
        image = R.drawable.bg_activities,
        backgroundColor = ActivityColor,
        banner = banner,
    )

    val categoryTags: List<CategoryTag> = tags.map { tag ->
        CategoryTag(
            name = tag.name,
            isSelected = currentSelectedTags.firstOrNull { it.name == tag.name } != null
        )
    }
}

@Keep
sealed class ActivitiesPartialState {
    data class Error(val message: String) : ActivitiesPartialState()
    object Loading : ActivitiesPartialState()
    object OnFilterReset : ActivitiesPartialState()
    data class OnBannerSuccess(val banner: GifBanner?) : ActivitiesPartialState()
    data class OnRecommendedDialog(val item: RecommendedCategoryDetails?) : ActivitiesPartialState()
    data class OnFilterDialog(val isVisible: Boolean) : ActivitiesPartialState()
    data class OnFiltersSelected(val data: List<ActivitiesTag>) : ActivitiesPartialState()
    data class OnCategoriesSelected(val data: ActivitiesCategory) : ActivitiesPartialState()
    data class OnCategoriesSuccess(val data: List<ActivitiesCategory>) : ActivitiesPartialState()
    data class OnTagSuccess(val data: List<ActivitiesTag>) : ActivitiesPartialState()
    data class OnActivitiessSuccess(
        val items: List<CategoryItem>,
        val recommendedItems: List<RecommendedCategoryDetails>,
        val shouldDisplayFilter: Boolean,
    ) : ActivitiesPartialState()
}

/**
 *  Here Intent is the same as Intent in MVI architecture
 *  Google likes to call it Event as Android already has an Intent class
 */
@Keep
sealed class ActivitiesUiEvent {
    data class OnCategoryClicked(val position: Int) : ActivitiesUiEvent()
    data class OnFilterClicked(val isVisible: Boolean) : ActivitiesUiEvent()
    object OnResetClicked : ActivitiesUiEvent()
    data class OnFiltersSelected(val filters: List<String>) : ActivitiesUiEvent()
    data class OnRecommendedItemSelected(val id: String) : ActivitiesUiEvent()
    object OnRecommendedDialogClosed : ActivitiesUiEvent()
}
