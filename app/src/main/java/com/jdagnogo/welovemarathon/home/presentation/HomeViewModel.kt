package com.jdagnogo.welovemarathon.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.ui.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.home.domain.Blog
import com.jdagnogo.welovemarathon.home.domain.HomeUseCases
import com.jdagnogo.welovemarathon.home.domain.MarathonRun
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val reducer: HomeReducer,
) : ViewModel(),
    IModel<HomeState, HomeUiEvent> {
    private val _state = MutableStateFlow(HomeState())
    override val state: StateFlow<HomeState> get() = _state

    init {
        dispatchEvent(HomeUiEvent.FetchRuns)
        dispatchEvent(HomeUiEvent.FetchBlogs)
    }

    private fun fetchBlogs() {
        viewModelScope.launch {
            delay(1000)
            homeUseCases.getBlogUseCase.invoke().onEach { resource ->
                val partialState = when (resource) {
                    is Resource.Success -> {
                        HomePartialState.OnBlogsSuccess(resource.data ?: listOf())
                    }
                    is Resource.Loading -> HomePartialState.LoadingBlogs
                    else -> {
                        //Todo : hanlde error
                        HomePartialState.Error("")
                    }
                }
                _state.value =  reducer.reduce(_state.value, partialState)
            }.launchIn(this)
        }
    }

    private fun fetchRuns() {
        viewModelScope.launch {
            delay(1000)
            homeUseCases.getRunUseCase.invoke().onEach { resource ->
                val partialState = when (resource) {
                    is Resource.Success -> {
                        HomePartialState.OnRunsSuccess(resource.data ?: listOf())
                    }
                    is Resource.Loading -> HomePartialState.LoadingRuns
                    else -> {
                        //Todo : hanlde error
                        HomePartialState.Error("")
                    }
                }
                _state.value = reducer.reduce(_state.value, partialState)
            }.launchIn(this)
        }
    }

    override fun dispatchEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnBlogClicked -> TODO()
            HomeUiEvent.FetchBlogs -> fetchBlogs()
            HomeUiEvent.FetchRuns -> fetchRuns()
        }
    }
}

/**
 * The data class that will describe the state of the view
 */
data class HomeState(
    val isLoadingBlogs: Boolean = true,
    val isLoadingRuns: Boolean = true,
    val blogs: List<Blog> = listOf(),
    val runs: List<MarathonRun> = listOf(),
    val error: String = "",
)

sealed class HomePartialState {
    object LoadingBlogs : HomePartialState()
    object LoadingRuns : HomePartialState()
    data class Error(val message: String) : HomePartialState()
    data class OnBlogsSuccess(val data: List<Blog>) : HomePartialState()
    data class OnRunsSuccess(val data: List<MarathonRun>) : HomePartialState()
}

/**
 *  Here Intent is the same as Intent in MVI architecture
 *  Google likes to call it Event as Android already has an Intent class
 */
sealed class HomeUiEvent {
    data class OnBlogClicked(val id: String) : HomeUiEvent()
    object FetchBlogs : HomeUiEvent()
    object FetchRuns : HomeUiEvent()
}
