package com.jdagnogo.welovemarathon.shopping.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.R
import com.jdagnogo.welovemarathon.common.banner.GifBanner
import com.jdagnogo.welovemarathon.common.banner.SHOPPING
import com.jdagnogo.welovemarathon.common.submenu.SubMenuUiModel
import com.jdagnogo.welovemarathon.common.ui.theme.ShoppingColor
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.handleResource
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategories
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategory
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val useCases: ShoppingUseCase,
    private val reducer: ShoppingReducer,
) : ViewModel(), IModel<ShoppingState, ShoppingUiEvent> {

    private val _state = MutableStateFlow(ShoppingState())
    override val state: StateFlow<ShoppingState> get() = _state

    init {
        fetchCategories()
        fetchBanner()
    }

    private var currentSelected: ShoppingCategories? = null

    private fun fetchCategories() {
        viewModelScope.launch {
            handleResource(
                useCases.getShoppingCategoriesUseCase.invoke(),
                { ShoppingPartialState.OnCategoriesSuccess(it) },
                ShoppingPartialState.Loading,
                { ShoppingPartialState.Error("") },
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
                        ShoppingPartialState.OnBannerSuccess(
                            resource.data?.firstOrNull()
                                ?: GifBanner()
                        )
                    }
                    is Resource.Loading -> {
                        ShoppingPartialState.Loading
                    }
                    else -> {
                        ShoppingPartialState.Error(resource.message ?: "")
                    }
                }
                _state.value = reducer.reduce(_state.value, partialState)
            }.launchIn(this)
        }
    }

    /* private fun fetchShopping(category: ShoppingCategories) {
         viewModelScope.launch {
             if (currentSelected == category) return@launch
             currentSelected = category
             val data = useCases.getShopping(category)
             val recommended = data.firstOrNull { it.isRecommended }
             data.toMutableList().remove(recommended)
             val partialState = ShoppingPartialState.OnShoppingsSuccess(data = data,
                 recommended,
                 category)
             _state.value = reducer.reduce(_state.value, partialState)
         }
     }*/

    override fun dispatchEvent(event: ShoppingUiEvent) {
        when (event) {
            is ShoppingUiEvent.OnCategoryClicked -> {
            }
            ShoppingUiEvent.OnMapSelected -> {
            }
        }
    }
}

/**
 * The data class that will describe the state of the view
 */
@Keep
data class ShoppingState(
    val currentSelected: ShoppingCategories = ShoppingCategories.Woman,
    val categories: List<ShoppingCategory> = listOf(),
    val shoppings: List<Shopping> = listOf(),
    val recommended: Shopping? = null,
    val banner: GifBanner? = null,
    val error: String = "",
) {
    val subMenuUiModel = SubMenuUiModel(
        screenName = "Shopping",
        items = categories.map { it.toSubMenuItem() },
        image = R.drawable.beach,
        backgroundColor = ShoppingColor,
        banner = banner,
    )
}

@Keep
sealed class ShoppingPartialState {
    data class Error(val message: String) : ShoppingPartialState()
    object Loading : ShoppingPartialState()
    data class OnBannerSuccess(val banner: GifBanner?) : ShoppingPartialState()
    data class OnCategoriesSuccess(val data: List<ShoppingCategory>) : ShoppingPartialState()
    data class OnShoppingsSuccess(
        val data: List<Shopping>,
        val recommended: Shopping?,
        val currentSelected: ShoppingCategories,
    ) :
        ShoppingPartialState()
}

/**
 *  Here Intent is the same as Intent in MVI architecture
 *  Google likes to call it Event as Android already has an Intent class
 */
sealed class ShoppingUiEvent {
    data class OnCategoryClicked(val position: Int) : ShoppingUiEvent()
    object OnMapSelected : ShoppingUiEvent()
}
