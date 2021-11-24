package com.jdagnogo.welovemarathon.food.domain.restaurant

import coil.network.HttpException
import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

interface RestaurantRemoteData {
    suspend fun getRestaurants(): Resource<List<Restaurant>>
}

class RestaurantFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    RestaurantRemoteData {
    override suspend fun getRestaurants(): Resource<List<Restaurant>> {
        val restaurants = mutableListOf<Restaurant>()
        return try {
            val snapshot = fireStore
                .collection(COLLECTION_NAME)
                .get()
                .await()

            restaurants.addAll(snapshot.toObjects(Restaurant::class.java))

            Resource.Success(restaurants)
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
        private const val COLLECTION_NAME = "Restaurant"
    }
}
