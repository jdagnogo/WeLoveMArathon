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
import com.jdagnogo.welovemarathon.food.data.FoodCategoryEntity
import com.jdagnogo.welovemarathon.food.data.FoodEntity
import com.jdagnogo.welovemarathon.food.data.FoodDao
import com.jdagnogo.welovemarathon.food.data.FoodTagEntity
import com.jdagnogo.welovemarathon.run.data.RunDao
import com.jdagnogo.welovemarathon.run.data.RunEntity
import com.jdagnogo.welovemarathon.shopping.data.ShoppingCategoryEntity
import com.jdagnogo.welovemarathon.shopping.data.ShoppingDao
import com.jdagnogo.welovemarathon.shopping.data.ShoppingEntity
import com.jdagnogo.welovemarathon.shopping.data.ShoppingTagEntity
import com.jdagnogo.welovemarathon.sport.data.SportCategoryEntity
import com.jdagnogo.welovemarathon.sport.data.SportDao
import com.jdagnogo.welovemarathon.sport.data.SportEntity
import com.jdagnogo.welovemarathon.tips.data.TipsDao
import com.jdagnogo.welovemarathon.tips.data.TipsEntity
import java.util.*

@Database(
    entities = [
        BlogEntity::class,
        RunEntity::class,
        FoodEntity::class,
        FoodCategoryEntity::class,
        FoodTagEntity::class,
        GifBannerEntity::class,
        BeachEntity::class,
        ShoppingEntity::class,
        ShoppingCategoryEntity::class,
        ShoppingTagEntity::class,
        DataFreshnessEntity::class,
        SportCategoryEntity::class,
        SportEntity::class,
        TipsEntity::class],
    version = 11
)
abstract class WLMDatabase : RoomDatabase() {
    abstract fun getBlogDao(): BlogDao
    abstract fun getRunDao(): RunDao
    abstract fun getFoodDao(): FoodDao
    abstract fun getBannerDao(): BannerDao
    abstract fun getBeachDao(): BeachDao
    abstract fun getShoppingDao(): ShoppingDao
    abstract fun getTipsDao(): TipsDao
    abstract fun getDataFreshnessDao(): DataFreshnessDao
    abstract fun getSportDao(): SportDao

    companion object {
        private const val DB_NAME = "wlm_db"
        const val SMALL_ITEM_COUNT = 2
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
