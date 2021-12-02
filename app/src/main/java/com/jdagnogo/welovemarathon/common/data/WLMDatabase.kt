package com.jdagnogo.welovemarathon.common.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jdagnogo.welovemarathon.beach.data.BeachDao
import com.jdagnogo.welovemarathon.beach.data.BeachEntity
import com.jdagnogo.welovemarathon.blog.data.BlogDao
import com.jdagnogo.welovemarathon.blog.data.BlogEntity
import com.jdagnogo.welovemarathon.common.banner.BannerDao
import com.jdagnogo.welovemarathon.common.banner.GifBannerEntity
import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantDao
import com.jdagnogo.welovemarathon.food.data.restaurant.RestaurantEntity
import com.jdagnogo.welovemarathon.run.data.RunDao
import com.jdagnogo.welovemarathon.run.data.RunEntity
import com.jdagnogo.welovemarathon.shopping.data.ShoppingDao
import com.jdagnogo.welovemarathon.shopping.data.ShoppingEntity
import java.util.*

@Database(
    entities = [
        BlogEntity::class,
        RunEntity::class,
        RestaurantEntity::class,
        GifBannerEntity::class,
        BeachEntity::class,
        ShoppingEntity::class],
    version = 1
)
abstract class WLMDatabase : RoomDatabase() {
    abstract fun getBlogDao(): BlogDao
    abstract fun getRunDao(): RunDao
    abstract fun getRestaurantDao(): RestaurantDao
    abstract fun getBannerDao(): BannerDao
    abstract fun getBeachDao(): BeachDao
    abstract fun getShoppingDao(): ShoppingDao

    companion object {
        private const val DB_NAME = "wlm_db"
        const val SMALL_ITEM_COUNT = 5
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
