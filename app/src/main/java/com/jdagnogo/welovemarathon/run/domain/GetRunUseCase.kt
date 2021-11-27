package com.jdagnogo.welovemarathon.run.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.run.data.RunRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRunUseCase @Inject constructor(private val repository: RunRepository) {
    suspend operator fun invoke(): Flow<Resource<List<Run>>> {
        return repository.getRuns()
    }
}
