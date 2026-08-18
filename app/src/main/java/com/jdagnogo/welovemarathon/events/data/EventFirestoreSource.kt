package com.jdagnogo.welovemarathon.events.data

import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.fetchList
import javax.inject.Inject

interface EventFirestoreSource {
    suspend fun fetchAll(): Resource<List<EventDto>>
}

class EventFirebaseSource @Inject constructor(
    private val fireStore: FirebaseFirestore,
) : EventFirestoreSource {
    override suspend fun fetchAll(): Resource<List<EventDto>> {
        return fetchList<EventDto>(fireStore, COLLECTION_NAME)
    }

    companion object {
        private const val COLLECTION_NAME = "events"
    }
}
