package com.jdagnogo.welovemarathon.activities.di

import com.jdagnogo.welovemarathon.activities.data.ActivitiesFirebaseData
import com.jdagnogo.welovemarathon.activities.data.ActivitiesRemoteData
import com.jdagnogo.welovemarathon.activities.data.ActivitiesRepository
import com.jdagnogo.welovemarathon.activities.data.ActivitiesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivitiesModuleData {
    @Binds
    abstract fun bindActivitiesRepository(
        activitiesRepositoryImpl: ActivitiesRepositoryImpl,
    ): ActivitiesRepository

    @Binds
    abstract fun bindBlogFirebaseData(
        restaurantFirebaseData: ActivitiesFirebaseData,
    ): ActivitiesRemoteData
}
