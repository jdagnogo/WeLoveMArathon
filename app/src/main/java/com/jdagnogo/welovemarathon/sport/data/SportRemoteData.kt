package com.jdagnogo.welovemarathon.sport.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import com.jdagnogo.welovemarathon.sport.domain.Sport
import com.jdagnogo.welovemarathon.sport.domain.SportCategory
import javax.inject.Inject

interface SportRemoteData {
    suspend fun getSports(): Resource<List<Sport>>
    suspend fun getSportCategories(): Resource<List<SportCategory>>
}

class SportFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    SportRemoteData {
    override suspend fun getSports(): Resource<List<Sport>> {
        return fetchList(fireStore, SPORT_COLLECTION_NAME)
    }

    override suspend fun getSportCategories(): Resource<List<SportCategory>> {
        return fetchList(fireStore, SPORT_CATEGORY_COLLECTION_NAME)
    }

    companion object {
        private const val SPORT_COLLECTION_NAME = "Sport"
        private const val SPORT_CATEGORY_COLLECTION_NAME = "SportCategory"
    }
}
