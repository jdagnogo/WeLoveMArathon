package com.jdagnogo.welovemarathon.wine.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import com.jdagnogo.welovemarathon.wine.domain.WineSocial
import com.jdagnogo.welovemarathon.wine.domain.WineTour
import com.jdagnogo.welovemarathon.wine.domain.WineryInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

interface WineRemoteData {
    suspend fun getWineTour(): Resource<List<WineTour>>
    suspend fun getWineSocial(): Resource<List<WineSocial>>
    suspend fun getWineInfo(): Resource<List<WineryInfo>>
}

@ExperimentalCoroutinesApi
class WineFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    WineRemoteData {

    override suspend fun getWineTour(): Resource<List<WineTour>> {
        return fetchList(fireStore, TOUR_COLLECTION_NAME)
    }

    override suspend fun getWineSocial(): Resource<List<WineSocial>> {
        return fetchList(fireStore, SOCIAL_COLLECTION_NAME)
    }

    override suspend fun getWineInfo(): Resource<List<WineryInfo>> {
        return fetchList(fireStore, INFO_COLLECTION_NAME)
    }

    companion object {
        private const val TOUR_COLLECTION_NAME = "WineTour"
        private const val SOCIAL_COLLECTION_NAME = "WineSocial"
        private const val INFO_COLLECTION_NAME = "WineInfo"
    }
}