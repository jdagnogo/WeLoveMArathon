package com.jdagnogo.welovemarathon.beach.domain

import com.jdagnogo.welovemarathon.beach.data.BeachRepository
import com.jdagnogo.welovemarathon.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPrivateBeachesUseCase @Inject constructor(private val repository: BeachRepository) {
    operator fun invoke(parentId: String): Flow<Resource<List<PrivateBeach>>> {
        return repository.privateBeaches
            .map { resource ->
                val result = resource.data?.filter { it.parentId == parentId }?.sortedBy { it.name }
                return@map Resource.Success(result)
            }
    }
}
