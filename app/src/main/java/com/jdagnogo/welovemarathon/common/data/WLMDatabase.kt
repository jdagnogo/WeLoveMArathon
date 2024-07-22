package com.jdagnogo.welovemarathon.common.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jdagnogo.welovemarathon.about.data.AboutDao
import com.jdagnogo.welovemarathon.about.data.AboutData
import com.jdagnogo.welovemarathon.about.data.AboutEntity
import com.jdagnogo.welovemarathon.activities.data.ActivitiesCategoryEntity
import com.jdagnogo.welovemarathon.activities.data.ActivitiesDao
import com.jdagnogo.welovemarathon.activities.data.ActivitiesEntity
import com.jdagnogo.welovemarathon.activities.data.ActivitiesTagEntity
import com.jdagnogo.welovemarathon.beach.data.BeachDao
import com.jdagnogo.welovemarathon.beach.data.BeachEntity
import com.jdagnogo.welovemarathon.blog.data.BlogDao
import com.jdagnogo.welovemarathon.blog.data.BlogEntity
import com.jdagnogo.welovemarathon.common.banner.BannerDao
import com.jdagnogo.welovemarathon.common.banner.GifBannerEntity
import com.jdagnogo.welovemarathon.common.like.data.FavDao
import com.jdagnogo.welovemarathon.common.like.data.FavoriteEntity
import com.jdagnogo.welovemarathon.common.utils.Converters
import com.jdagnogo.welovemarathon.culture.data.CultureDao
import com.jdagnogo.welovemarathon.culture.data.CultureEntity
import com.jdagnogo.welovemarathon.food.data.FoodCategoryEntity
import com.jdagnogo.welovemarathon.food.data.FoodEntity
import com.jdagnogo.welovemarathon.food.data.FoodDao
import com.jdagnogo.welovemarathon.food.data.FoodTagEntity
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantDao
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantEntity
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantFilterEntity
import com.jdagnogo.welovemarathon.shopping.data.ShoppingCategoryEntity
import com.jdagnogo.welovemarathon.shopping.data.ShoppingDao
import com.jdagnogo.welovemarathon.shopping.data.ShoppingEntity
import com.jdagnogo.welovemarathon.shopping.data.ShoppingTagEntity
import com.jdagnogo.welovemarathon.sport.data.SportCategoryEntity
import com.jdagnogo.welovemarathon.sport.data.SportDao
import com.jdagnogo.welovemarathon.sport.data.SportEntity
import com.jdagnogo.welovemarathon.tips.data.TipsDao
import com.jdagnogo.welovemarathon.tips.data.TipsEntity
import com.jdagnogo.welovemarathon.wine.data.WineDao
import com.jdagnogo.welovemarathon.wine.data.WineSocialEntity
import com.jdagnogo.welovemarathon.wine.data.WineTourEntity
import com.jdagnogo.welovemarathon.wine.data.WineryInfoEntity
import com.jdagnogo.welovemarathon.wine.domain.WineSocial
import com.jdagnogo.welovemarathon.wine.domain.WineTour
import java.util.*

@Database(
    entities = [
        BlogEntity::class,
        FoodEntity::class,
        FoodCategoryEntity::class,
        FoodTagEntity::class,
        ActivitiesEntity::class,
        ActivitiesCategoryEntity::class,
        ActivitiesTagEntity::class,
        GifBannerEntity::class,
        BeachEntity::class,
        ShoppingEntity::class,
        ShoppingCategoryEntity::class,
        ShoppingTagEntity::class,
        DataFreshnessEntity::class,
        SportCategoryEntity::class,
        SportEntity::class,
        CultureEntity::class,
        FavoriteEntity::class,
        WineTourEntity::class,
        WineSocialEntity::class,
        WineryInfoEntity::class,
        AboutEntity::class,
        RestaurantEntity::class,
        RestaurantFilterEntity::class,
        TipsEntity::class],
    version = 9
)
@TypeConverters(Converters::class)
abstract class WLMDatabase : RoomDatabase() {
    abstract fun getBlogDao(): BlogDao
    abstract fun getFoodDao(): FoodDao
    abstract fun getRestaurantDao(): RestaurantDao
    abstract fun getFavDao(): FavDao
    abstract fun getActivitiesDao(): ActivitiesDao
    abstract fun getBannerDao(): BannerDao
    abstract fun getBeachDao(): BeachDao
    abstract fun getShoppingDao(): ShoppingDao
    abstract fun getTipsDao(): TipsDao
    abstract fun getDataFreshnessDao(): DataFreshnessDao
    abstract fun getSportDao(): SportDao
    abstract fun getWineDao(): WineDao
    abstract fun getAboutDao(): AboutDao
    abstract fun getCultureDao(): CultureDao

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
