package com.jdagnogo.welovemarathon.wine.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.wine.data.WineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWineSocialUseCase @Inject constructor(private val repository: WineRepository) {
    operator fun invoke(): Flow<Resource<List<WineSocial>>> {
        return repository.social.map { list ->
            Resource.Success(list.data?.sortedBy { it.ordinal } ?: listOf())
        }
    }
}