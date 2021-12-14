package com.jdagnogo.welovemarathon.sport.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.sport.data.SportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSportCategoriesUseCase @Inject constructor(private val repository: SportRepository) {
    suspend operator fun invoke(): Flow<Resource<List<SportCategory>>> {
        return repository.categories.map { list ->
            Resource.Success(list.data ?: listOf())
        }
    }
}
