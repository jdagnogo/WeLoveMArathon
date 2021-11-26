package com.jdagnogo.welovemarathon.common.banner

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface BannerRepository {
    suspend fun getHomeBanner(forceFetch: Boolean = false): Flow<Resource<List<GifBanner>>>
}

class BannerRepositoryImpl @Inject constructor(
    private val bannerDao: BannerDao,
    private val bannerRemoteData: BannerRemoteData,
) :
    BannerRepository {
    override suspend fun getHomeBanner(forceFetch: Boolean): Flow<Resource<List<GifBanner>>> {
        return resourceAsFlow(
            forceFetch = forceFetch,
            fetchFromLocal = {
                bannerDao.getAll().map { banners -> banners.map { it.toGifBanner() } }
            },
            networkCall = { bannerRemoteData.getHomeBanner() },
            saveCallResource = { blogs ->
                val blogEntities = blogs.map { it.toGifEntity() }
                bannerDao.update(blogEntities)
            },
            checkDataFreshness = { false }
        )
    }
}
