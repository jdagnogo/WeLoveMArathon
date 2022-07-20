package com.jdagnogo.welovemarathon.about.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.about.domain.About
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import com.jdagnogo.welovemarathon.shopping.domain.transformContent
import javax.inject.Inject

interface AboutRemoteData {
    suspend fun getAbout(): Resource<About>
}

class AboutFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    AboutRemoteData {

    override suspend fun getAbout(): Resource<About> {
        return fetchList<About>(fireStore, COLLECTION_NAME).transformContent {
            it?.firstOrNull() ?: About()
        }
    }

    companion object {
        private const val COLLECTION_NAME = "About"
    }
}