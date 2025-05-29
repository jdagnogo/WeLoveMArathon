package com.jdagnogo.welovemarathon.activities.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.activities.data.ActivitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetActivitiesTagUseCase @Inject constructor(private val repository: ActivitiesRepository) {
    operator fun invoke(category: String): Flow<Resource<List<ActivitiesTag>>> {
        return repository.tags.map { list ->
            Resource.Success(list.data?.sortedBy { it.name }?.filter {
                it.category.contains(category, ignoreCase = true)
            } ?: listOf())
        }
    }
}
