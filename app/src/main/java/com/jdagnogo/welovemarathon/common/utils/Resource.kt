package com.jdagnogo.welovemarathon.common.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    sealed class GenericError<T> : Resource<T>() {
        class NetworkError<T>(message: String, data: T? = null) : GenericError<T>()
        class HttpError<T>(message: String, data: T? = null, code: Int = 500) : GenericError<T>()
    }
}
