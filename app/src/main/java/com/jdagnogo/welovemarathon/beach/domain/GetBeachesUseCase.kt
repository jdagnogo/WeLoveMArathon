package com.jdagnogo.welovemarathon.beach.domain

import com.jdagnogo.welovemarathon.beach.data.BeachRepository
import com.jdagnogo.welovemarathon.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBeachesUseCase @Inject constructor(private val repository: BeachRepository) {
    suspend operator fun invoke(): Flow<Resource<List<Beach>>> {
        return repository.getBeaches()
    }
}
