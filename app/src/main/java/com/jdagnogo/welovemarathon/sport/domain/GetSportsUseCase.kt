package com.jdagnogo.welovemarathon.sport.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.sport.data.SportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSportsUseCase @Inject constructor(private val repository: SportRepository) {
    suspend operator fun invoke(type: String): Flow<Resource<List<Sport>>> {
        return repository.data.map { list ->
            val result =
                list.data
                    ?.filter { it.category == type }
                    ?.sortedBy { it.name }?.toMutableList() ?: listOf()
            return@map Resource.Success(result)
        }
    }
}
