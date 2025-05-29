package com.jdagnogo.welovemarathon.common.banner

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BannerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(banners: List<GifBannerEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Transaction
    suspend fun update(banner: List<GifBannerEntity>) {
        clear()
        insertAll(banner)
    }

    @Query(QUERY_GET_HOME)
    fun getAll(): Flow<List<GifBannerEntity>>

    companion object {
        private const val QUERY_DELETE = "DELETE FROM ${GifBannerEntity.TABLE_BANNER}"
        private const val QUERY_GET_HOME = "SELECT * FROM ${GifBannerEntity.TABLE_BANNER}"
    }
}
