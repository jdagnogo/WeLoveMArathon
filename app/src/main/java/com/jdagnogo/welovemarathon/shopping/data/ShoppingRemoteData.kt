package com.jdagnogo.welovemarathon.shopping.data

import androidx.constraintlayout.compose.DesignElements.map
import coil.network.HttpException
import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import com.jdagnogo.welovemarathon.shopping.domain.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

interface ShoppingRemoteData {
    suspend fun getShoppings(): Resource<List<Shopping>>
}

@ExperimentalCoroutinesApi
class ShoppingFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    ShoppingRemoteData {
    override suspend fun getShoppings(): Resource<List<Shopping>> {
        val shoppings = mutableListOf<Shopping>()
        return try {
            val documents = fireStore
                .collection(COLLECTION_NAME)
                .get()
                .await()

            withContext(Dispatchers.Default) {
                for (document in documents) {
                    Shopping().apply {
                        map(document.data)
                        shoppings.add(this)
                    }
                }
            }

            Resource.Success(shoppings)
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
        private const val COLLECTION_NAME = "Shopping"
    }
}
