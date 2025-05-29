package com.jdagnogo.welovemarathon.about.data

import com.jdagnogo.welovemarathon.about.domain.About
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

interface AboutRepository {
    val about: StateFlow<Resource<About>>
}

class AboutRepositoryImpl @Inject constructor(
    private val aboutData: AboutData,
    private val coroutineScope: CoroutineScope,
) : AboutRepository {
    private val _about: MutableStateFlow<Resource<About>> =
        MutableStateFlow(Resource.Loading(About()))
    override val about: StateFlow<Resource<About>>
        get() = _about

    init {
        fetchData()
    }

    private fun fetchData(forceFetch: Boolean = false) {
        coroutineScope.launch {
            with(aboutData) {
                val data = resourceAsFlow(
                    forceFetch = forceFetch,
                    fetchFromLocal = { aboutDao.getAll().map { aboutMapper.toAbout(it) } },
                    networkCall = { aboutRemoteData.getAbout() },
                    saveCallResource = { about ->
                        val beachEntities = aboutMapper.toAboutEntity(about)
                        aboutDao.update(beachEntities)
                    },
                    checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.ABOUT) })

                data.collectLatest {
                    _about.value = it
                }
            }
        }
    }
}