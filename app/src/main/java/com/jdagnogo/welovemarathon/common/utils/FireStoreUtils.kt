package com.jdagnogo.welovemarathon.common.utils

import coil.network.HttpException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.io.IOException

suspend inline fun <reified T> fetchList(
    fireStore: FirebaseFirestore,
    collectionName: String,
): Resource<List<T>> {
    return try {
        val data = mutableListOf<T>()
        val snapshot = fireStore
            .collection(collectionName)
            .get()
            .await()
        data.addAll(snapshot.toObjects(T::class.java))

        Resource.Success(data)
    } catch (e: HttpException) {
        Resource.GenericError.HttpError(e.message ?: "", null, code = e.response.code())
    } catch (e: IOException) {
        Resource.GenericError.NetworkError(
            message = "Couldn't reach server, check your internet connection.",
            data = listOf()
        )
    }
}
