package com.jdagnogo.welovemarathon.home.data.blog

import androidx.room.*
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
        private const val QUERY_DELETE = "DELETE FROM blog_entity"
        private const val QUERY_GET_ALL = "SELECT * FROM blog_entity"
    }
}
