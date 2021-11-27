package com.jdagnogo.welovemarathon.blog.domain

import com.jdagnogo.welovemarathon.blog.data.BlogRepository
import com.jdagnogo.welovemarathon.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBlogUseCase @Inject constructor(private val repository: BlogRepository) {
    suspend operator fun invoke(): Flow<Resource<List<Blog>>> {
        return repository.getBlogs()
    }
}
