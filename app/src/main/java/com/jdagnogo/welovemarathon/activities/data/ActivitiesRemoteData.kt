package com.jdagnogo.welovemarathon.activities.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import com.jdagnogo.welovemarathon.activities.domain.Activities
import com.jdagnogo.welovemarathon.activities.domain.ActivitiesCategory
import com.jdagnogo.welovemarathon.activities.domain.ActivitiesTag
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

interface ActivitiesRemoteData {
    suspend fun get(): Resource<List<Activities>>
    suspend fun getCategories(): Resource<List<ActivitiesCategory>>
    suspend fun getTags(): Resource<List<ActivitiesTag>>
}

@ExperimentalCoroutinesApi
class ActivitiesFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    ActivitiesRemoteData {
    override suspend fun get(): Resource<List<Activities>> {
        return fetchList(fireStore, COLLECTION_NAME)
    }

    override suspend fun getCategories(): Resource<List<ActivitiesCategory>> {
        return fetchList(fireStore, CATEGORY_COLLECTION_NAME)
    }

    override suspend fun getTags(): Resource<List<ActivitiesTag>> {
        return fetchList(fireStore, TAG_COLLECTION_NAME)
    }

    companion object {
        private const val COLLECTION_NAME = "Activities"
        private const val CATEGORY_COLLECTION_NAME = "ActivitiesCategories"
        private const val TAG_COLLECTION_NAME = "ActivitiesTags"
    }
}
