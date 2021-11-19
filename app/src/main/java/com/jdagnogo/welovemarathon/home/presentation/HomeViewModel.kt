package com.jdagnogo.welovemarathon.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.home.domain.Blog
import com.jdagnogo.welovemarathon.home.domain.GetBlogUseCase
import com.jdagnogo.welovemarathon.home.domain.fakeList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getBlogUseCase: GetBlogUseCase) : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> get() = _state

    private val _eventFlow = MutableSharedFlow<HomeUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchBlogs()
    }

    private fun fetchBlogs() {
        viewModelScope.launch {
            getBlogUseCase.invoke().onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _state.value = HomeState.OnBlogSuccess(resource.data ?: listOf())
                    }
                    is Resource.Error -> HomeState.Error(resource.message ?: "")
                    is Resource.Loading -> HomeState.Loading
                }
            }.launchIn(this)
        }
    }
}

sealed class HomeState {
    object Loading : HomeState()
    data class Error(val message: String) : HomeState()
    data class OnBlogSuccess(val data: List<Blog>) : HomeState()
}

sealed class HomeUiEvent {
    data class OnBlogClicked(val id: String) : HomeUiEvent()
}
