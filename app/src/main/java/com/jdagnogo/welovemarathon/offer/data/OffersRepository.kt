package com.jdagnogo.welovemarathon.offer.data

import com.jdagnogo.welovemarathon.common.domain.DataType
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.offer.domain.Offer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

interface OfferRepository {
    val data: StateFlow<Resource<List<Offer>>>
}

class OfferRepositoryImpl @Inject constructor(
    private val offerData: OfferData,
    private val coroutineScope: CoroutineScope,
) : OfferRepository {
    private val _data: MutableStateFlow<Resource<List<Offer>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val data: StateFlow<Resource<List<Offer>>>
        get() = _data


    init {
        fetchData()
    }


    private fun fetchData(forceFetch: Boolean = false) {
        coroutineScope.launch {
            with(offerData) {
                val categories = resourceAsFlow(
                    forceFetch = forceFetch,
                    fetchFromLocal = {
                        dao.getAll().map { mapper.toDomain(it) }
                    },
                    networkCall = { remoteData.getOffers() },
                    saveCallResource = { sports ->
                        val sportEntities = mapper.toEntities(sports)
                        dao.update(sportEntities)
                    },
                    checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.ACTIVITIES) })

                categories.collectLatest {
                    _data.value = it
                }
            }
        }
    }
}
