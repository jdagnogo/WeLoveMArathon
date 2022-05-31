package com.jdagnogo.welovemarathon.culture.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import com.jdagnogo.welovemarathon.culture.domain.Culture
import javax.inject.Inject

interface CultureRemoteData {
    suspend fun getCultureCategories(): Resource<List<Culture>>
}

class CultureFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    CultureRemoteData {

    override suspend fun getCultureCategories(): Resource<List<Culture>> {
        return fetchList<Culture>(fireStore, COLLECTION_NAME)
    }

    companion object {
        private const val COLLECTION_NAME = "Culture"
    }
}
