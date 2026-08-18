package com.jdagnogo.welovemarathon.events.di

import com.jdagnogo.welovemarathon.events.data.EventFirebaseSource
import com.jdagnogo.welovemarathon.events.data.EventFirestoreSource
import com.jdagnogo.welovemarathon.events.data.EventRepository
import com.jdagnogo.welovemarathon.events.data.EventRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class EventsDataModule {
    @Binds
    abstract fun bindEventFirebaseSource(
        eventFirebaseSource: EventFirebaseSource,
    ): EventFirestoreSource

    @Binds
    abstract fun bindEventRepository(
        eventRepositoryImpl: EventRepositoryImpl,
    ): EventRepository
}
