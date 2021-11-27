package com.jdagnogo.welovemarathon.run.data

import coil.network.HttpException
import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.run.domain.Run
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

interface RunRemoteData {
    suspend fun getRuns(): Resource<List<Run>>
}

@ExperimentalCoroutinesApi
class RunFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    RunRemoteData {
    override suspend fun getRuns(): Resource<List<Run>> {
        val runs = mutableListOf<Run>()
        return try {
            val snapshot = fireStore
                .collection(COLLECTION_NAME)
                .get()
                .await()

            runs.addAll(snapshot.toObjects(Run::class.java))

            Resource.Success(runs)
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
        private const val COLLECTION_NAME = "Run"
    }
}
