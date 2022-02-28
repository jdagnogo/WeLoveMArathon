package com.jdagnogo.welovemarathon.shopping.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import com.jdagnogo.welovemarathon.shopping.domain.Shopping
import com.jdagnogo.welovemarathon.shopping.domain.ShoppingCategory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

interface ShoppingRemoteData {
    suspend fun get(): Resource<List<Shopping>>
    suspend fun getCategories(): Resource<List<ShoppingCategory>>
}

@ExperimentalCoroutinesApi
class ShoppingFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    ShoppingRemoteData {
    override suspend fun get(): Resource<List<Shopping>> {
        return fetchList(fireStore, COLLECTION_NAME)
    }

    override suspend fun getCategories(): Resource<List<ShoppingCategory>> {
        return fetchList(fireStore, CATEGORY_COLLECTION_NAME)
    }

    companion object {
        private const val COLLECTION_NAME = "Shopping"
        private const val CATEGORY_COLLECTION_NAME = "ShoppingCategory"
    }
}
