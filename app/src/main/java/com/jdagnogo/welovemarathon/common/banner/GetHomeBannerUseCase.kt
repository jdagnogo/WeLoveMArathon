package com.jdagnogo.welovemarathon.common.banner

import com.jdagnogo.welovemarathon.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeBannerUseCase @Inject constructor(private val repository: BannerRepository) {
    suspend operator fun invoke(): Flow<Resource<List<GifBanner>>> {
        return repository.getHomeBanner(forceFetch = false)
    }
}
