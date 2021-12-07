package com.jdagnogo.welovemarathon.food.data.restaurant

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import com.jdagnogo.welovemarathon.food.domain.restaurant.Food
import javax.inject.Inject

interface RestaurantRemoteData {
    suspend fun getFood(type: String): Resource<List<Food>>
}

class RestaurantFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    RestaurantRemoteData {
    override suspend fun getFood(type: String): Resource<List<Food>> {
        return fetchList(fireStore, type)
    }
}
