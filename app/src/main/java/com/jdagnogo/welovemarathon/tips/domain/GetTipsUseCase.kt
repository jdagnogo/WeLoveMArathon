package com.jdagnogo.welovemarathon.tips.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.tips.data.TipsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetTipsUseCase @Inject constructor(private val repository: TipsRepository) {
    suspend operator fun invoke(): Flow<Resource<List<Tips>>> {
        return repository.getTips().onEach {
            it.data?.forEach { tips ->
                tips.description = tips.description.replace("\\n", "\n\n    • ").replace("\\l", "\n")
            }
        }
    }
}
