package com.jdagnogo.welovemarathon.blog.data

import com.jdagnogo.welovemarathon.blog.domain.Blog
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface BlogRepository {
    suspend fun getBlogs(forceFetch: Boolean = false): Flow<Resource<List<Blog>>>
}

class BlogRepositoryImpl @Inject constructor(
    private val blogData: BlogData,
) : BlogRepository {
    override suspend fun getBlogs(forceFetch: Boolean): Flow<Resource<List<Blog>>> {
        with(blogData) {
            return resourceAsFlow(
                forceFetch = forceFetch,
                fetchFromLocal = { blogDao.getAll().map { blogMapper.toBlogs(it) } },
                networkCall = { blogRemoteData.getBlogs() },
                saveCallResource = { blogs ->
                    val blogEntities = blogMapper.toBlogsEntities(blogs)
                    blogDao.updateBlogs(blogEntities)
                },
                checkDataFreshness = { false }
            )
        }
    }
}
