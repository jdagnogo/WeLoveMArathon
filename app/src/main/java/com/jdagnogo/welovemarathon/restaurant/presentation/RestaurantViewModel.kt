package com.jdagnogo.welovemarathon.restaurant.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.GeoPoint
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.handleResource
import com.jdagnogo.welovemarathon.common.utils.handleResourceWithFav
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.food.domain.FoodCategory.Companion.ALL
import com.jdagnogo.welovemarathon.food.domain.FoodCategory.Companion.allCategory
import com.jdagnogo.welovemarathon.food.domain.FoodUseCase
import com.jdagnogo.welovemarathon.restaurant.domain.Amenities
import com.jdagnogo.welovemarathon.restaurant.domain.Plates
import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantAppliedFilter
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantFilter
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val useCases: FoodUseCase,
    private val reducer: RestaurantReducer,
) : ViewModel(), IModel<RestaurantState, RestaurantUiEvent> {
    private val _state = MutableStateFlow(RestaurantState())
    override val state: StateFlow<RestaurantState> get() = _state

    init {
        fetchCategories()
        dispatchEvent(event = RestaurantUiEvent.OnCategoryClicked(ALL))
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            handleResource(
                useCases.getFoodCategoriesUseCase.invoke(),
                {
                    RestaurantPartialState.OnCategoriesSuccess(it)
                },
                RestaurantPartialState.Loading,
                { RestaurantPartialState.Error("") },
                {
                    _state.value = reducer.reduce(state.value, it)
                },
                this
            )
        }
    }

    private fun fetchFood(category: FoodCategory) {
        viewModelScope.launch {
            viewModelScope.launch {
                handleResourceWithFav(
                    useCases.getFoodUseCase.invoke(category.name),
                    useCases.favUseCase.getAllFavUseCases(),
                    { it, favorites ->
                        RestaurantPartialState.OnFoodsSuccess(
                            recommendedItems = it.filter { it.isRecommended }
                                .map { it.toRecommendedCategoryItem() }
                        )
                    },
                    RestaurantPartialState.Loading,
                    { RestaurantPartialState.Error("") },
                    { partialState ->
                        _state.value = reducer.reduce(state.value, partialState)
                    },
                    this
                )
            }
        }
    }

    override fun dispatchEvent(event: RestaurantUiEvent) {
        when (event) {
            is RestaurantUiEvent.OnCategoryClicked -> {
                val category =
                    state.value.allCategories.firstOrNull { it.name == event.name } ?: return
                fetchFood(category)
                val partialState = RestaurantPartialState.OnCategoriesSelected(category)
                _state.value = reducer.reduce(state.value, partialState)
            }

            is RestaurantUiEvent.OnFilterClicked -> {}
            is RestaurantUiEvent.OnLikeClicked -> {}
            is RestaurantUiEvent.OnRestaurantClicked -> {
                val toto = state.value.recommendedItems.firstOrNull() ?: return
                val fakeIcon = state.value.currentCategorySelected.icon
                val restaurant = Restaurant(
                    id = "id",
                    name = "Fake restaurant",
                    website = "website",
                    location = "long long long long long  name",
                    locationLink = toto.locationLink,
                    number = toto.number,
                    description = toto.description,
                    isRecommended = false,
                    coordinate = GeoPoint(38.1342163,24.0146045),
                    services = listOf(
                        RestaurantService(
                            title = "Services", icon = fakeIcon, description = "Take away - blabla - blabla long long long long long stuff"
                        ),
                        RestaurantService(
                            title = "Cuisine",
                            icon = fakeIcon,
                            description = "Asian - Fusion - Soublaki"
                        ),
                        RestaurantService(
                            title = "senserit",
                            icon = fakeIcon,
                            description = "litora"
                        )
                    ),
                    plates = listOf(
                        Plates(
                            name = "Virginia Solis",
                            image = toto.images.last(),
                            bigImage = toto.bigImages.last()
                        ),
                        Plates(
                            name = "toto",
                            image = toto.images.first(),
                            bigImage = toto.bigImages.first()
                        ),
                        Plates(
                            name = "tata",
                            image = toto.images.first(),
                            bigImage = toto.bigImages.first()
                        ),
                        Plates(
                            name = "titi",
                            image = toto.images.first(),
                            bigImage = toto.bigImages.first()
                        )
                    ),
                    amenities = listOf(
                        Amenities(type = "ultrrtricies", "titi - ferfwbh - ewbfbh titi - ferfwbh - ewbfbh titi - ferfwbh - ewbfbh titi - ferfwbh - ewbfbh titi - ferfwbh - ewbfbh titi - ferfwbh - ewbfbh", icon = fakeIcon),
                        Amenities(type = "blal", "titi / ferbfheh / fefhh", icon = fakeIcon),
                        Amenities(type = "ultrrtrifefef", "titi", icon = fakeIcon),
                        Amenities(type = "t4ter", "titi", icon = fakeIcon),
                        Amenities(type = "yjuk", "titi", icon = fakeIcon),
                        Amenities(type = "erewr", "titi", icon = fakeIcon),
                        Amenities(type = "gregrg", "titi", icon = fakeIcon),
                        Amenities(type = "gregreg", "titi"),
                    ),
                    images = toto.images,
                    bigImages = toto.bigImages

                ) //TODO : real data
                val partialState = RestaurantPartialState.OnRestaurantSelected(restaurant)
                _state.value = reducer.reduce(state.value, partialState)
            }

            RestaurantUiEvent.OnRestaurantReset -> {
                val partialState = RestaurantPartialState.OnRestaurantReset
                _state.value = reducer.reduce(state.value, partialState)
            }
        }
    }


}

@Keep
data class RestaurantState(
    val currentCategorySelected: FoodCategory = FoodCategory(),
    val categories: List<FoodCategory> = listOf(),
    val foods: List<CategoryItem> = listOf(),
    val recommendedItems: List<RecommendedCategoryDetails> = emptyList(),
    val currentRestaurantSelected: Restaurant? = null,
    val filter: RestaurantFilter = RestaurantFilter(),
    val currentFilterApplied: RestaurantAppliedFilter = RestaurantAppliedFilter(),
) {
    val allCategories = listOf(allCategory).plus(categories)
}

@Keep
sealed class RestaurantPartialState {
    data class Error(val message: String) : RestaurantPartialState()
    data object Loading : RestaurantPartialState()
    data class OnCategoriesSelected(val data: FoodCategory) : RestaurantPartialState()
    data class OnRestaurantSelected(val data: Restaurant) : RestaurantPartialState()
    data object OnRestaurantReset : RestaurantPartialState()
    data class OnCategoriesSuccess(val data: List<FoodCategory>) : RestaurantPartialState()

    data class OnFoodsSuccess(val recommendedItems: List<RecommendedCategoryDetails>) :
        RestaurantPartialState()
}

@Keep
sealed class RestaurantUiEvent {
    data class OnCategoryClicked(val name: String) : RestaurantUiEvent()
    data class OnRestaurantClicked(val name: String) : RestaurantUiEvent()
    data object OnRestaurantReset : RestaurantUiEvent()
    data class OnFilterClicked(val isVisible: Boolean) : RestaurantUiEvent()
    data class OnLikeClicked(val id: String) : RestaurantUiEvent()
}