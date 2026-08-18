package com.jdagnogo.welovemarathon.events.di

import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import com.jdagnogo.welovemarathon.events.data.EventDao
import com.jdagnogo.welovemarathon.events.data.EventMapper
import com.jdagnogo.welovemarathon.events.data.EventRepository
import com.jdagnogo.welovemarathon.events.domain.GetEventsUseCase
import com.jdagnogo.welovemarathon.events.domain.RefreshEventsUseCase
import com.jdagnogo.welovemarathon.events.presentation.EventsReducer
import com.jdagnogo.welovemarathon.events.presentation.NotificationSettingsReducer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventsModule {

    @Provides
    @Singleton
    fun provideEventDao(db: WLMDatabase): EventDao = db.getEventDao()

    @Provides
    @Singleton
    fun provideEventMapper(): EventMapper = EventMapper()

    @Provides
    @Singleton
    fun provideEventsReducer() = EventsReducer()

    @Provides
    @Singleton
    fun provideNotificationSettingsReducer() = NotificationSettingsReducer()

    @Provides
    @Singleton
    fun provideGetEventsUseCase(repository: EventRepository) = GetEventsUseCase(repository)

    @Provides
    @Singleton
    fun provideRefreshEventsUseCase(repository: EventRepository) = RefreshEventsUseCase(repository)
}
