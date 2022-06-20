package com.jdagnogo.welovemarathon.common.utils

import com.jdagnogo.welovemarathon.common.like.domain.Favorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

sealed class Resource<T>(var data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    sealed class GenericError<T> : Resource<T>() {
        class NetworkError<T>(message: String, data: T? = null) : GenericError<T>()
        class HttpError<T>(message: String, data: T? = null, code: Int = 500) : GenericError<T>()
    }
}

fun <Entity, PartialState> handleResource(
    useCase: Flow<Resource<List<Entity>>>,
    onSuccess: (toto: List<Entity>) -> PartialState,
    onLoading: PartialState,
    onError: () -> PartialState,
    sendToReducer: (PartialState) -> Unit,
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
        //  _state.value = reducer.reduce(_state.value, partialState)
        sendToReducer(partialState)
    }.launchIn(scope)
}

fun <Entity, PartialState> handleResourceWithFav(
    useCase: Flow<Resource<List<Entity>>>,
    fav: StateFlow<List<Favorite>>,
    onSuccess: (toto: List<Entity>, fav: List<Favorite>) -> PartialState,
    onLoading: PartialState,
    onError: () -> PartialState,
    sendToReducer: (PartialState) -> Unit,
    scope: CoroutineScope,
) {
    useCase.combine(fav) { entity, favorite ->
        val partialState = when (entity) {
            is Resource.Success -> {
                onSuccess(entity.data ?: listOf(), favorite)
            }
            is Resource.Loading -> onLoading
            else -> {
                onError()
            }
        }
        sendToReducer(partialState)
    }.launchIn(scope)
}
