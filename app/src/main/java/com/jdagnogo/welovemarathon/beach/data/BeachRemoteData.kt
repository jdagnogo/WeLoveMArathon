package com.jdagnogo.welovemarathon.beach.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.beach.domain.Beach
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import javax.inject.Inject

interface BeachRemoteData {
    suspend fun getBeaches(): Resource<List<Beach>>
}

class BeachFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    BeachRemoteData {

    override suspend fun getBeaches(): Resource<List<Beach>> {
        return fetchList<Beach>(fireStore, COLLECTION_NAME)
    }

    companion object {
        private const val COLLECTION_NAME = "Beach"
    }
}
