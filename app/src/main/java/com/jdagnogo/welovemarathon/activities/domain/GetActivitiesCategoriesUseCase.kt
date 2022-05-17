package com.jdagnogo.welovemarathon.activities.domain

import com.jdagnogo.welovemarathon.activities.data.ActivitiesRepository
import com.jdagnogo.welovemarathon.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetActivitiesCategoriesUseCase @Inject constructor(private val repository: ActivitiesRepository) {
    operator fun invoke(): Flow<Resource<List<ActivitiesCategory>>> {
        return repository.categories.map { list ->
            Resource.Success(list.data?.sortedBy { it.ordinal } ?: listOf())
        }
    }
}
