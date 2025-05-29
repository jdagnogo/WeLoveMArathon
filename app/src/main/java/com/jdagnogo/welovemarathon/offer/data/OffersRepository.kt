package com.jdagnogo.welovemarathon.offer.data

import com.jdagnogo.welovemarathon.common.domain.DataType
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.offer.domain.Offer
import com.jdagnogo.welovemarathon.offer.domain.OfferActivated
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

interface OfferRepository {
    val data: StateFlow<Resource<List<Offer>>>
    val offerActivated: StateFlow<Resource<List<OfferActivated>>>
    suspend fun updateOffer(offer: OfferActivated)
}

@Singleton
class OfferRepositoryImpl @Inject constructor(
    private val offerData: OfferData,
    private val coroutineScope: CoroutineScope,
) : OfferRepository {
    private val _data: MutableStateFlow<Resource<List<Offer>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val data: StateFlow<Resource<List<Offer>>>
        get() = _data

    private val _offerActivated: MutableStateFlow<Resource<List<OfferActivated>>> =
        MutableStateFlow(Resource.Loading(listOf()))
    override val offerActivated: StateFlow<Resource<List<OfferActivated>>>
        get() = _offerActivated

    override suspend fun updateOffer(offer: OfferActivated) {
        offerData.dao.insertAllOfferActivatedEntity(listOf(offer.toOfferActivatedEntity()))
    }


    init {
        fetchData()
        fetchOfferActivated()
    }

    private fun fetchOfferActivated() {
        coroutineScope.launch {
            offerData.dao.getAllOfferActivatedEntity().collectLatest {
                _offerActivated.value = Resource.Success(it.map { it.toOfferActivated() })
            }
        }
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
