package com.jdagnogo.welovemarathon.about.domain

import com.jdagnogo.welovemarathon.about.data.AboutRepository
import com.jdagnogo.welovemarathon.common.utils.Resource
import com.jdagnogo.welovemarathon.shopping.domain.transformContent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAboutUseCase @Inject constructor(private val repository: AboutRepository) {
    operator fun invoke(): Flow<Resource<About>> {
        return repository.about.transformContent { about ->
            About(
                policy = about?.policy ?: "",
                mail = about?.mail ?: "",
                phone = about?.phone ?: "",
                members = about?.members?.sortedBy { it.name } ?: emptyList(),
                socialMedias = about?.socialMedias?.sortedBy { it.ordinal } ?: emptyList(),
                photos = about?.photos ?: emptyList(),
            )
        }
    }
}