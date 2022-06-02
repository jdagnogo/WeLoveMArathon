package com.jdagnogo.welovemarathon.tips.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import com.jdagnogo.welovemarathon.tips.domain.Tips
import javax.inject.Inject

interface TipsRemoteData {
    suspend fun getTips(): Resource<List<Tips>>
}

class TipsFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    TipsRemoteData {
    override suspend fun getTips(): Resource<List<Tips>> {
        return fetchList<Tips>(fireStore, COLLECTION_NAME)
    }

    companion object {
        private const val COLLECTION_NAME = "Tips"
    }
}
