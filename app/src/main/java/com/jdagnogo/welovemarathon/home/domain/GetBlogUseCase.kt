package com.jdagnogo.welovemarathon.home.domain

import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.home.data.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBlogUseCase @Inject constructor(private val repository: HomeRepository) {

    suspend operator fun invoke(): Flow<Resource<List<Blog>>> {
        return repository.getBlogs()
    }
}
