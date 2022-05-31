package com.jdagnogo.welovemarathon.culture.domain

import com.jdagnogo.welovemarathon.culture.data.CultureRepository
import com.jdagnogo.welovemarathon.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCulturesUseCase @Inject constructor(private val repository: CultureRepository) {
    operator fun invoke(): Flow<Resource<List<Culture>>> {
        return repository.cultures
    }
}
