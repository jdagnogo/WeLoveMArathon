package com.jdagnogo.welovemarathon.culture.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jdagnogo.welovemarathon.common.banner.GifBanner
import com.jdagnogo.welovemarathon.common.category.CategoryItem
import com.jdagnogo.welovemarathon.common.category.RecommendedCategoryDetails
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.handleResource
import com.jdagnogo.welovemarathon.culture.domain.Culture
import com.jdagnogo.welovemarathon.culture.domain.CultureUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalPagerApi
@HiltViewModel
class CultureViewModel @Inject constructor(
    private val culturesUseCase: CultureUseCases,
    private val reducer: CultureReducer,
) : ViewModel(), IModel<CultureState, CultureUiEvent> {
    private val _state = MutableStateFlow(CultureState())
    override val state: StateFlow<CultureState> get() = _state

    init {
        fetchCultures()
    }

    override fun dispatchEvent(event: CultureUiEvent) {
        when (event) {
            is CultureUiEvent.OnCultureSelected -> {
                state.value.cultures.firstOrNull { event.id == it.id }?.let {
                    val partialState = CulturePartialState.OnCultureSelected(it)
                    _state.value = reducer.reduce(state.value, partialState)
                }
            }
            is CultureUiEvent.OnRecommendedItemSelected -> {
                val item = state.value.recommendedItems.firstOrNull { it.id == event.id }
                val partialState = CulturePartialState.OnRecommendedDialog(item = item)
                _state.value = reducer.reduce(state.value, partialState)
            }

            CultureUiEvent.OnRecommendedDialogClosed -> {}
        }
    }

    private fun fetchCultures() {
        viewModelScope.launch {
            handleResource(
                culturesUseCase.getCulturesUseCase.invoke(),
                { cultures ->
                    CulturePartialState.OnCultureSuccess(
                        cultures = cultures,
                    )
                },
                CulturePartialState.Loading,
                { CulturePartialState.Error("") },
                { partialState ->
                    _state.value = reducer.reduce(state.value, partialState)
                },
                this
            )
        }
    }
}

@Keep
data class CultureState(
    val mainScreenName: String = "Culture",
    val currentSelected: Culture = Culture(),
    val categories: List<CategoryItem> = listOf(),
    val cultures: List<Culture> = listOf(),
    val currentCultureSelected: RecommendedCategoryDetails? = null,
    val recommendedItems: List<RecommendedCategoryDetails> = emptyList(),
    val shouldOpenRecommendedDialog: Boolean = false,
    val banner: GifBanner? = null,
    val error: String = "",
) {
    val longCategoryItems = cultures.map { it.toLongCategoryItem() }
    val typeTwoItem = currentSelected.toTypeTwoItem()
}

@Keep
sealed class CulturePartialState {
    object Loading : CulturePartialState()
    data class OnRecommendedDialog(val item: RecommendedCategoryDetails?) : CulturePartialState()
    data class Error(val message: String) : CulturePartialState()
    data class OnCultureSuccess(val cultures: List<Culture>) : CulturePartialState()
    data class OnCultureSelected(val culture: Culture) : CulturePartialState()
    data class OnCulturesBarsRecommendedSuccess(val recommendedItems: List<RecommendedCategoryDetails>) :
        CulturePartialState()
}

@Keep
sealed class CultureUiEvent {
    data class OnCultureSelected(val id: String) : CultureUiEvent()
    data class OnRecommendedItemSelected(val id: String) : CultureUiEvent()
    object OnRecommendedDialogClosed : CultureUiEvent()
}
