package com.jdagnogo.welovemarathon.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.home.domain.Blog
import com.jdagnogo.welovemarathon.home.domain.HomeUseCases
import com.jdagnogo.welovemarathon.home.domain.MarathonRun
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeUseCases: HomeUseCases) : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.LoadingBlogs)
    val state: StateFlow<HomeState> get() = _state

    private val _eventFlow = MutableSharedFlow<HomeUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchRuns()
        fetchBlogs()
    }

    private fun fetchBlogs() {
        viewModelScope.launch {
            homeUseCases.getBlogUseCase.invoke().onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _state.value = HomeState.OnBlogsSuccess(resource.data ?: listOf())
                    }
                    is Resource.Error -> HomeState.Error(resource.message ?: "")
                    is Resource.Loading -> HomeState.LoadingBlogs
                    is Resource.GenericError.HttpError -> {
                    }
                    is Resource.GenericError.NetworkError -> {
                    }
                }
            }.launchIn(this)
        }
    }

    private fun fetchRuns() {
        viewModelScope.launch {
            homeUseCases.getRunUseCase.invoke().onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _state.value = HomeState.OnRunsSuccess(resource.data ?: listOf())
                    }
                    is Resource.Error -> HomeState.Error(resource.message ?: "")
                    is Resource.Loading -> HomeState.LoadingRuns
                    is Resource.GenericError.HttpError -> {
                    }
                    is Resource.GenericError.NetworkError -> {
                    }
                }
            }.launchIn(this)
        }
    }
}

sealed class HomeState {
    object LoadingBlogs : HomeState()
    object LoadingRuns : HomeState()
    data class Error(val message: String) : HomeState()
    data class OnBlogsSuccess(val data: List<Blog>) : HomeState()
    data class OnRunsSuccess(val data: List<MarathonRun>) : HomeState()
}

sealed class HomeUiEvent {
    data class OnBlogClicked(val id: String) : HomeUiEvent()
}
