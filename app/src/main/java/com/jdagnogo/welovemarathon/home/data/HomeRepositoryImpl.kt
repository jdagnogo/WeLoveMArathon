package com.jdagnogo.welovemarathon.home.data

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.home.data.blog.BlogDao
import com.jdagnogo.welovemarathon.home.data.blog.BlogMapper
import com.jdagnogo.welovemarathon.home.data.blog.BlogRemoteData
import com.jdagnogo.welovemarathon.home.domain.Blog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface HomeRepository {
    suspend fun getBlogs(forceFetch: Boolean = false): Flow<Resource<List<Blog>>>
}

class HomeRepositoryImpl @Inject constructor(
    private val blogDao: BlogDao,
    private val blogRemoteData: BlogRemoteData,
    private val blogMapper: BlogMapper,
) : HomeRepository {
    override suspend fun getBlogs(forceFetch: Boolean): Flow<Resource<List<Blog>>> {
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
