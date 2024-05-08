package com.jdagnogo.welovemarathon.common.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

fun <T, A> resourceAsFlow(
    forceFetch: Boolean = false,
    fetchFromLocal: () -> Flow<T>,
    networkCall: suspend () -> Resource<A>,
    saveCallResource: suspend (A) -> Unit,
    checkDataFreshness: suspend () -> Boolean = { true },
) = flow {
    emit(Resource.Loading())
    val isDataFresh = checkDataFreshness()
    if (forceFetch || !isDataFresh) {
        // if the data are not fresh or the user asked for a fetch => we request data from firebase
        when (val responseStatus = networkCall.invoke()) {
            is Resource.Success -> {
                // if it s a success, we save the data
                saveCallResource(responseStatus.data!!)
                emitAll(fetchFromLocal().map { dbData ->
                    Resource.Success(dbData)
                })
            }
            is Resource.Error -> {
                // If it s an error, we emit the latest value from the db
                emitAll(fetchFromLocal().map {
                    Resource.Error(responseStatus.message ?: "", it)
                })
            }
            is Resource.Loading -> {
            }

            else -> {}
        }
    } else {
        emitAll(fetchFromLocal().map { dbData ->
            Resource.Success(dbData)
        })
    }
}
