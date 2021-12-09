package com.jdagnogo.welovemarathon.tips.data

import com.jdagnogo.welovemarathon.common.domain.DataType
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.common.utils.resourceAsFlow
import com.jdagnogo.welovemarathon.tips.domain.Tips
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface TipsRepository {
    suspend fun getTips(forceFetch: Boolean = false): Flow<Resource<List<Tips>>>
}

class TipsRepositoryImpl @Inject constructor(private val tipsData: TipsData) : TipsRepository {
    override suspend fun getTips(forceFetch: Boolean): Flow<Resource<List<Tips>>> {
        with(tipsData) {
            return resourceAsFlow(
                forceFetch = forceFetch,
                fetchFromLocal = { dao.getAll().map { mapper.toTips(it) } },
                networkCall = { remoteData.getTips() },
                saveCallResource = { tipses ->
                    val tipsEntities = mapper.toTipsEntities(tipses)
                    dao.update(tipsEntities)
                },
                checkDataFreshness = { dataFreshnessUseCase.isDataFresh(DataType.TIPS) })
        }
    }
}
