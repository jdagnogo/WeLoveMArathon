package com.jdagnogo.welovemarathon.blog.data

import androidx.room.*
import com.jdagnogo.welovemarathon.blog.data.BlogEntity.Companion.TABLE_BLOG
import kotlinx.coroutines.flow.Flow

@Dao
interface BlogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(blogs: List<BlogEntity>)

    @Query(QUERY_DELETE)
    suspend fun clear()

    @Query(QUERY_GET_ALL)
    fun getAll(): Flow<List<BlogEntity>>

    @Transaction
    suspend fun updateBlogs(blogs: List<BlogEntity>) {
        clear()
        insertAll(blogs)
    }

    companion object {
        private const val QUERY_DELETE = "DELETE FROM $TABLE_BLOG"
        private const val QUERY_GET_ALL = "SELECT * FROM $TABLE_BLOG"
    }
}
