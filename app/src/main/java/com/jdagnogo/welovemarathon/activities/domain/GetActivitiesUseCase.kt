package com.jdagnogo.welovemarathon.activities.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.activities.data.ActivitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetActivitiesUseCase @Inject constructor(
    private val repository: ActivitiesRepository,
) {
    operator fun invoke(
        type: String? = null,
        tags: List<String> = emptyList()
    ): Flow<Resource<List<Activities>>> {
        return repository.data.map { list ->
            var result =
                list.data?.sortedBy { it.name }?.toMutableList() ?: listOf()
            if (type != null) {
                result = result.filter { it.category.contains(type) }
            }

            if (tags.isNotEmpty()) {
                return@map Resource.Success(result.filter { activities ->
                    var containsTags = false
                    tags.forEach { tag ->
                        if (activities.tags.contains(tag, ignoreCase = true)) {
                            containsTags = true
                            return@forEach
                        }
                    }
                    containsTags
                })
            }
            return@map Resource.Success(result)
        }
    }
}
