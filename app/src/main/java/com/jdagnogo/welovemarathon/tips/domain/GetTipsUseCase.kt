package com.jdagnogo.welovemarathon.tips.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.shopping.domain.transformContent
import com.jdagnogo.welovemarathon.tips.data.TipsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTipsUseCase @Inject constructor(private val repository: TipsRepository) {
    suspend operator fun invoke(): Flow<Resource<List<Tips>>> {
        return repository.getTips().transformContent { categories ->
            categories?.sortedBy { it.ordinal } ?: listOf()
        }
    }
}
