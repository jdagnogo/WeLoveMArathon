package com.jdagnogo.welovemarathon.common.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jdagnogo.welovemarathon.home.data.blog.BlogDao
import com.jdagnogo.welovemarathon.home.data.blog.BlogEntity
import com.jdagnogo.welovemarathon.home.data.run.RunEntity
import java.util.*

@Database(
    entities = [BlogEntity::class, RunEntity::class],
    version = 1
)
abstract class WLMDatabase : RoomDatabase() {
    abstract fun getBlogDao(): BlogDao

    companion object {
        private const val DB_NAME = "wlm_db"
        private lateinit var INSTANCE: WLMDatabase

        fun getAppDataBase(context: Context): WLMDatabase {
            if (::INSTANCE.isInitialized.not()) {
                synchronized(WLMDatabase::class) {
                    val language = Locale.getDefault().language
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WLMDatabase::class.java,
                        DB_NAME + language
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE
        }
    }
}
