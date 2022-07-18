package com.jdagnogo.welovemarathon.beach.domain

import com.jdagnogo.welovemarathon.beach.data.BeachRepository
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.shopping.domain.transformContent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBeachesUseCase @Inject constructor(private val repository: BeachRepository) {
    operator fun invoke(): Flow<Resource<List<Beach>>> {
        return repository.beaches.transformContent { categories ->
            categories?.sortedBy { it.ordinal }?: emptyList()
        }
    }
}
