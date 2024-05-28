package com.jdagnogo.welovemarathon.restaurant.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import com.jdagnogo.welovemarathon.restaurant.domain.Restaurant
import javax.inject.Inject

interface RestaurantRemoteData {
    suspend fun get(): Resource<List<Restaurant>>
}

class RestaurantFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    RestaurantRemoteData {
    override suspend fun get(): Resource<List<Restaurant>> {
        return fetchList(fireStore, COLLECTION_NAME)
    }

    companion object {
        private const val COLLECTION_NAME = "Food"
    }
}