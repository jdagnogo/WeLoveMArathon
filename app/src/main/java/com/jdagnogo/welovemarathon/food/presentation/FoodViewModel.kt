package com.jdagnogo.welovemarathon.food.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.banner.GifBanner
import com.jdagnogo.welovemarathon.common.banner.SHOPPING
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.CategoryTag
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails
import com.jdagnogo.welovemarathon.common.submenu.SubMenuUiModel
import com.jdagnogo.welovemarathon.common.ui.theme.FoodColor
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.handleResource
import com.jdagnogo.welovemarathon.common.utils.handleResourceWithFav
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.food.domain.FoodTag
import com.jdagnogo.welovemarathon.food.domain.FoodUseCase
import com.jdagnogo.welovemarathon.shopping.presentation.ShoppingUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val useCases: FoodUseCase,
    private val reducer: FoodReducer,
) : ViewModel(), IModel<FoodState, FoodUiEvent> {

    private val _state = MutableStateFlow(FoodState())
    override val state: StateFlow<FoodState> get() = _state

    init {
        fetchCategories()
        fetchBanner()
    }

    private var currentSelected: FoodCategory? = null

    private fun fetchCategories() {
        viewModelScope.launch {
            handleResource(
                useCases.getFoodCategoriesUseCase.invoke(),
                { FoodPartialState.OnCategoriesSuccess(it) },
                FoodPartialState.Loading,
                { FoodPartialState.Error("") },
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
                useCases.getFoodTagUseCase.invoke(category = category),
                { FoodPartialState.OnTagSuccess(it) },
                FoodPartialState.Loading,
                { FoodPartialState.Error("") },
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
                        FoodPartialState.OnBannerSuccess(
                            resource.data?.firstOrNull()
                                ?: GifBanner()
                        )
                    }
                    is Resource.Loading -> {
                        FoodPartialState.Loading
                    }
                    else -> {
                        FoodPartialState.Error(resource.message ?: "")
                    }
                }
                _state.value = reducer.reduce(_state.value, partialState)
            }.launchIn(this)
        }
    }

    private fun fetchFood(category: FoodCategory, tags: List<String> = emptyList()) {
        viewModelScope.launch {
            currentSelected = category
            viewModelScope.launch {
                handleResourceWithFav(
                    useCases.getFoodUseCase.invoke(category.name, tags),
                    useCases.favUseCase.getAllFavUseCases(),
                    { it, favorites ->
                        FoodPartialState.OnFoodsSuccess(
                            items = it.filter { it.isRecommended.not() }
                                .map { it.toCategoryItem(favorites.firstOrNull { fav -> fav.id == it.id } != null) },
                            recommendedItems = it.filter { it.isRecommended }
                                .map { it.toRecommendedCategoryItem() }
                        )
                    },
                    FoodPartialState.Loading,
                    { FoodPartialState.Error("") },
                    { partialState ->
                        _state.value = reducer.reduce(state.value, partialState)
                    },
                    this
                )
            }
        }
    }

    override fun dispatchEvent(event: FoodUiEvent) {
        when (event) {
            is FoodUiEvent.OnCategoryClicked -> {
                val category = state.value.categories[event.position]
                fetchTags(category = category.name)
                fetchFood(category)
                val partialState = FoodPartialState.OnCategoriesSelected(category)
                _state.value = reducer.reduce(state.value, partialState)
            }
            is FoodUiEvent.OnFilterClicked -> {
                val partialState = FoodPartialState.OnFilterDialog(event.isVisible)
                _state.value = reducer.reduce(state.value, partialState)
            }
            is FoodUiEvent.OnRecommendedItemSelected -> {
                val item = state.value.recommendedItems.firstOrNull { it.id == event.id }
                val partialState = FoodPartialState.OnRecommendedDialog(item = item)
                _state.value = reducer.reduce(state.value, partialState)
            }
            FoodUiEvent.OnRecommendedDialogClosed -> {
                val partialState = FoodPartialState.OnRecommendedDialog(item = null)
                _state.value = reducer.reduce(state.value, partialState)
            }
            is FoodUiEvent.OnFiltersSelected -> {
                fetchFood(
                    state.value.currentSelected,
                    event.filters
                )
                val partialState = FoodPartialState.OnFiltersSelected(
                    event.filters.map { FoodTag(it) }
                )
                _state.value = reducer.reduce(state.value, partialState)
            }
            FoodUiEvent.OnResetClicked -> {
                fetchFood(
                    state.value.currentSelected,
                    emptyList()
                )
                val partialState = FoodPartialState.OnFilterReset
                _state.value = reducer.reduce(state.value, partialState)
            }
            is FoodUiEvent.OnLikeClicked -> {
                viewModelScope.launch {
                    val activities = state.value.foods.firstOrNull {
                        it.id == event.id
                    }
                    useCases.favUseCase.updateFavUseCase(activities)
                }
            }
        }
    }
}

/**
 * The data class that will describe the state of the view
 */
@Keep
data class FoodState(
    val currentSelected: FoodCategory = FoodCategory(),
    val categories: List<FoodCategory> = listOf(),
    val currentSelectedTags: List<FoodTag> = listOf(),
    val tags: List<FoodTag> = listOf(),
    val foods: List<CategoryItem> = listOf(),
    val currentFoodSelected: RecommendedCategoryDetails? = null,
    val recommendedItems: List<RecommendedCategoryDetails> = emptyList(),
    val shouldOpenRecommendedDialog: Boolean = false,
    val shouldOpenFilterDialog: Boolean = false,
    val banner: GifBanner? = null,
    val error: String = "",
) {
    val subMenuUiModel = SubMenuUiModel(
        screenName = "Food",
        items = categories.map { it.toSubMenuItem() },
        image = R.drawable.bg_food,
        backgroundColor = FoodColor,
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
sealed class FoodPartialState {
    data class Error(val message: String) : FoodPartialState()
    object Loading : FoodPartialState()
    object OnFilterReset : FoodPartialState()
    data class OnBannerSuccess(val banner: GifBanner?) : FoodPartialState()
    data class OnRecommendedDialog(val item: RecommendedCategoryDetails?) : FoodPartialState()
    data class OnFilterDialog(val isVisible: Boolean) : FoodPartialState()
    data class OnFiltersSelected(val data: List<FoodTag>) : FoodPartialState()
    data class OnCategoriesSelected(val data: FoodCategory) : FoodPartialState()
    data class OnCategoriesSuccess(val data: List<FoodCategory>) : FoodPartialState()
    data class OnTagSuccess(val data: List<FoodTag>) : FoodPartialState()
    data class OnFoodsSuccess(
        val items: List<CategoryItem>,
        val recommendedItems: List<RecommendedCategoryDetails>
    ) : FoodPartialState()
}

/**
 *  Here Intent is the same as Intent in MVI architecture
 *  Google likes to call it Event as Android already has an Intent class
 */
@Keep
sealed class FoodUiEvent {
    data class OnCategoryClicked(val position: Int) : FoodUiEvent()
    data class OnFilterClicked(val isVisible: Boolean) : FoodUiEvent()
    object OnResetClicked : FoodUiEvent()
    data class OnLikeClicked(val id: String) : FoodUiEvent()
    data class OnFiltersSelected(val filters: List<String>) : FoodUiEvent()
    data class OnRecommendedItemSelected(val id: String) : FoodUiEvent()
    object OnRecommendedDialogClosed : FoodUiEvent()
}
