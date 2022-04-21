package com.jdagnogo.welovemarathon.food.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import com.jdagnogo.welovemarathon.food.domain.Food
import com.jdagnogo.welovemarathon.food.domain.FoodCategory
import com.jdagnogo.welovemarathon.food.domain.FoodTag
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

interface FoodRemoteData {
    suspend fun get(): Resource<List<Food>>
    suspend fun getCategories(): Resource<List<FoodCategory>>
    suspend fun getTags(): Resource<List<FoodTag>>
}

@ExperimentalCoroutinesApi
class FoodFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    FoodRemoteData {
    override suspend fun get(): Resource<List<Food>> {
        return fetchList(fireStore, COLLECTION_NAME)
    }

    override suspend fun getCategories(): Resource<List<FoodCategory>> {
        return fetchList(fireStore, CATEGORY_COLLECTION_NAME)
    }

    override suspend fun getTags(): Resource<List<FoodTag>> {
        return fetchList(fireStore, TAG_COLLECTION_NAME)
    }

    companion object {
        private const val COLLECTION_NAME = "Restaurant"
        private const val CATEGORY_COLLECTION_NAME = "FoodCategories"
        private const val TAG_COLLECTION_NAME = "FoodTags"
    }
}
