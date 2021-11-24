package com.jdagnogo.welovemarathon.common.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.jdagnogo.welovemarathon.common.WLMApp
import com.jdagnogo.welovemarathon.common.data.WLMDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
    ): WLMDatabase {
        return WLMDatabase.getAppDataBase(app)
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()
}
