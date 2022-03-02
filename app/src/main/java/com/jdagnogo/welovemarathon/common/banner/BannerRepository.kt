package com.jdagnogo.welovemarathon.common.banner

import com.jdagnogo.welovemarathon.common.domain.DataFreshnessUseCase
import com.jdagnogo.welovemarathon.common.domain.DataType
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

interface BannerRepository {
    val data: StateFlow<Resource<List<GifBanner>>>
}

class BannerRepositoryImpl @Inject constructor(
    private val bannerDao: BannerDao,
    private val bannerRemoteData: BannerRemoteData,
    private val dataFreshnessUseCase: DataFreshnessUseCase,
    private val coroutineScope: CoroutineScope,
) :
    BannerRepository {
    private val _data: MutableStateFlow<Resource<List<GifBanner>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val data: StateFlow<Resource<List<GifBanner>>>
        get() = _data

    init {
        coroutineScope.launch {
            getBanners(true)
        }
    }

    private suspend fun getBanners(forceFetch: Boolean) {
        coroutineScope.launch {
            val data = resourceAsFlow(
                forceFetch = forceFetch,
                fetchFromLocal = {
                    bannerDao.getAll().map { banners -> banners.map { it.toGifBanner() } }
                },
                networkCall = { bannerRemoteData.getHomeBanner() },
                saveCallResource = { blogs ->
                    val blogEntities = blogs.map { it.toGifEntity() }
                    bannerDao.update(blogEntities)
                },
                checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.BANNER) })
            data.collectLatest {
                _data.value = it
            }
        }
    }
}
