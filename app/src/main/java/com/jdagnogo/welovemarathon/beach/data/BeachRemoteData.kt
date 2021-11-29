package com.jdagnogo.welovemarathon.beach.data

import coil.network.HttpException
import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.common.utils.Resource
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

interface BeachRemoteData {
    suspend fun getBeaches(): Resource<List<Beach>>
}

class BeachFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    BeachRemoteData {
    override suspend fun getBeaches(): Resource<List<Beach>> {
        val beaches = mutableListOf<Beach>()
        return try {
            val snapshot = fireStore
                .collection(COLLECTION_NAME)
                .get()
                .await()

            beaches.addAll(snapshot.toObjects(Beach::class.java))

            Resource.Success(beaches)
        } catch (e: HttpException) {
            Resource.GenericError.HttpError(e.message ?: "", null, code = e.response.code())
        } catch (e: IOException) {
            Resource.GenericError.NetworkError(
                message = "Couldn't reach server, check your internet connection.",
                data = listOf()
            )
        }
    }

    companion object {
        private const val COLLECTION_NAME = "Beach"
    }
}
