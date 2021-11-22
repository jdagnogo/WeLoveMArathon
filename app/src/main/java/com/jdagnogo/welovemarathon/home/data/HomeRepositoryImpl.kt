package com.jdagnogo.welovemarathon.home.data

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.home.domain.Blog
import com.jdagnogo.welovemarathon.home.domain.MarathonRun
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface HomeRepository {
    suspend fun getBlogs(forceFetch: Boolean = false): Flow<Resource<List<Blog>>>
    suspend fun getRuns(forceFetch: Boolean = false): Flow<Resource<List<MarathonRun>>>
}

class HomeRepositoryImpl @Inject constructor(
    private val homeData: HomeData,
) : HomeRepository {
    override suspend fun getBlogs(forceFetch: Boolean): Flow<Resource<List<Blog>>> {
        with(homeData) {
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

    override suspend fun getRuns(forceFetch: Boolean): Flow<Resource<List<MarathonRun>>> {
        with(homeData) {
            return resourceAsFlow(
                forceFetch = forceFetch,
                fetchFromLocal = { runDao.getAll().map { runMapper.toRuns(it) } },
                networkCall = { runRemoteData.getRuns() },
                saveCallResource = { runs ->
                    val runEntities = runMapper.toRunsEntities(runs)
                    runDao.updateRuns(runEntities)
                },
                checkDataFreshness = { false }
            )
        }
    }
}
