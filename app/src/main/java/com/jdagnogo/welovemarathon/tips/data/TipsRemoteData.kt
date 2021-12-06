package com.jdagnogo.welovemarathon.tips.data

import coil.network.HttpException
import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.tips.domain.Tips
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

interface TipsRemoteData {
    suspend fun getTips(): Resource<List<Tips>>
}

class TipsFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    TipsRemoteData {
    override suspend fun getTips(): Resource<List<Tips>> {
        val tips = mutableListOf<Tips>()
        return try {
            val snapshot = fireStore
                .collection(COLLECTION_NAME)
                .get()
                .await()

            tips.addAll(snapshot.toObjects(Tips::class.java))

            Resource.Success(tips)
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
        private const val COLLECTION_NAME = "Tips"
    }
}
