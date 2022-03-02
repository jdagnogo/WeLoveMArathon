package com.jdagnogo.welovemarathon.common.banner

import com.jdagnogo.welovemarathon.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBannerUseCase @Inject constructor(private val repository: BannerRepository) {
    operator fun invoke(type: String): Flow<Resource<List<GifBanner>>> {
        return repository.data.map { list ->
            val result =
                list.data
                    ?.filter { it.type == type }
            return@map Resource.Success(result)
        }
    }
}
