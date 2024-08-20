package com.jdagnogo.welovemarathon.offer.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import com.jdagnogo.welovemarathon.offer.domain.Offer
import javax.inject.Inject

interface OfferRemoteData {
    suspend fun getOffers(): Resource<List<Offer>>
}

class OfferFirebaseData @Inject constructor(private val fireStore: FirebaseFirestore) :
    OfferRemoteData {

    override suspend fun getOffers(): Resource<List<Offer>> {
        return fetchList<Offer>(fireStore, COLLECTION_NAME)
    }

    companion object {
        private const val COLLECTION_NAME = "Offer"
    }
}