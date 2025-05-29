package com.jdagnogo.welovemarathon.common.banner

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import javax.inject.Inject

interface BannerRemoteData {
    suspend fun getHomeBanner(): Resource<List<GifBanner>>
}

class BannerFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    BannerRemoteData {
    override suspend fun getHomeBanner(): Resource<List<GifBanner>> {
        return fetchList<GifBanner>(fireStore, COLLECTION_NAME)
    }

    companion object {
        private const val COLLECTION_NAME = "HomeBanner"
    }
}
