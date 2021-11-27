package com.jdagnogo.welovemarathon.home.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.blog.domain.Blog
import com.jdagnogo.welovemarathon.common.banner.GifBanner
import com.jdagnogo.welovemarathon.common.ui.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.home.domain.HomeUseCases
import com.jdagnogo.welovemarathon.run.domain.Run
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
        dispatchEvent(HomeUiEvent.FetchBanner)
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
                _state.value = reducer.reduce(_state.value, partialState)
            }.launchIn(this)
        }
    }

    private fun fetchBanner() {
        viewModelScope.launch {
            homeUseCases.getHomeBannerUseCase.invoke().onEach { resource ->
                val partialState = when (resource) {
                    is Resource.Success -> {
                        HomePartialState.OnBannerSuccess(resource.data?.first())
                    }
                    else -> {
                        //Todo : hanlde error
                        HomePartialState.Error("")
                    }
                }
                _state.value = reducer.reduce(_state.value, partialState)
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
            HomeUiEvent.FetchBanner -> fetchBanner()
        }
    }
}

/**
 * The data class that will describe the state of the view
 */
@Keep
data class HomeState(
    val isLoadingBlogs: Boolean = true,
    val isLoadingRuns: Boolean = true,
    val banner: GifBanner? = null,
    val blogs: List<Blog> = listOf(),
    val runs: List<Run> = listOf(),
    val error: String = "",
)

@Keep
sealed class HomePartialState {
    object LoadingBlogs : HomePartialState()
    object LoadingRuns : HomePartialState()
    data class Error(val message: String) : HomePartialState()
    data class OnBannerSuccess(val banner: GifBanner?) : HomePartialState()
    data class OnBlogsSuccess(val data: List<Blog>) : HomePartialState()
    data class OnRunsSuccess(val data: List<Run>) : HomePartialState()
}

/**
 *  Here Intent is the same as Intent in MVI architecture
 *  Google likes to call it Event as Android already has an Intent class
 */
sealed class HomeUiEvent {
    data class OnBlogClicked(val id: String) : HomeUiEvent()
    object FetchBlogs : HomeUiEvent()
    object FetchRuns : HomeUiEvent()
    object FetchBanner : HomeUiEvent()
}
