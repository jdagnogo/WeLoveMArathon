package com.jdagnogo.welovemarathon.run.presentation

import androidx.annotation.Keep
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.welovemarathon.blog.domain.Blog
import com.jdagnogo.welovemarathon.common.utils.IModel
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.run.domain.Run
import com.jdagnogo.welovemarathon.run.domain.RunUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunViewModel @Inject constructor(
    private val runUseCases: RunUseCases,
    private val reducer: RunReducer,
) : ViewModel(),
    IModel<RunState, RunUiEvent> {
    private val _state = MutableStateFlow(RunState())
    override val state: StateFlow<RunState> get() = _state

    init {
        dispatchEvent(RunUiEvent.FetchRuns)
        dispatchEvent(RunUiEvent.FetchBlogs)
    }

    private fun fetchBlogs() {
        viewModelScope.launch {
            handleResource(runUseCases.getBlogUseCase.invoke(),
                { RunPartialState.OnBlogsSuccess(it) },
                RunPartialState.Loading,
                { RunPartialState.Error("") },
                this)
        }
    }

    private fun fetchRuns() {
        viewModelScope.launch {
            delay(1000)
            handleResource(runUseCases.getRunUseCase.invoke(),
                { RunPartialState.OnRunsSuccess(it) },
                RunPartialState.Loading,
                { RunPartialState.Error("") },
                this)
        }
    }

    /**
     * this method avoid code duplication
     */
    private fun <T> handleResource(
        useCase: Flow<Resource<List<T>>>,
        onSuccess: (toto: List<T>) -> RunPartialState,
        onLoading: RunPartialState,
        onError: () -> RunPartialState,
        scope: CoroutineScope,
    ) {
        useCase.onEach { resource ->
            val partialState = when (resource) {
                is Resource.Success -> {
                    onSuccess(resource.data ?: listOf())
                }
                is Resource.Loading -> onLoading
                else -> {
                    onError()
                }
            }
            _state.value = reducer.reduce(_state.value, partialState)
        }.launchIn(scope)
    }

    override fun dispatchEvent(event: RunUiEvent) {
        when (event) {
            is RunUiEvent.OnBlogClicked -> TODO()
            RunUiEvent.FetchBlogs -> fetchBlogs()
            RunUiEvent.FetchRuns -> fetchRuns()
        }
    }
}

/**
 * The data class that will describe the state of the view
 */
@Keep
data class RunState(
    val blogs: List<Blog> = listOf(),
    val runs: List<Run> = listOf(),
    val error: String = "",
)

@Keep
sealed class RunPartialState {
    object Loading : RunPartialState()
    data class Error(val message: String) : RunPartialState()
    data class OnBlogsSuccess(val data: List<Blog>) : RunPartialState()
    data class OnRunsSuccess(val data: List<Run>) : RunPartialState()
}

/**
 *  Here Intent is the same as Intent in MVI architecture
 *  Google likes to call it Event as Android already has an Intent class
 */
sealed class RunUiEvent {
    data class OnBlogClicked(val id: String) : RunUiEvent()
    object FetchBlogs : RunUiEvent()
    object FetchRuns : RunUiEvent()
}
