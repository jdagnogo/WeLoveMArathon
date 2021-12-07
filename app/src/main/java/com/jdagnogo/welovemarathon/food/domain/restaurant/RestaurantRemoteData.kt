package com.jdagnogo.welovemarathon.food.domain.restaurant

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import javax.inject.Inject

interface RestaurantRemoteData {
    suspend fun getRestaurants(): Resource<List<Food>>
    suspend fun getDesserts(): Resource<List<Food>>
    suspend fun getCoffees(): Resource<List<Food>>
}

class RestaurantFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    RestaurantRemoteData {
    override suspend fun getRestaurants(): Resource<List<Food>> {
        return fetchList(fireStore, FoodCategory.RESTAURANT.title)
    }

    override suspend fun getDesserts(): Resource<List<Food>> {
        return fetchList(fireStore, FoodCategory.DESSERT.title)
    }

    override suspend fun getCoffees(): Resource<List<Food>> {
        return fetchList(fireStore, FoodCategory.COFFEE.title)
    }
}
