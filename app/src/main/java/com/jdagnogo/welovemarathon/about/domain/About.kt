package com.jdagnogo.welovemarathon.about.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.about.data.AboutEntity

@Keep
data class About(
    val members: List<Member>,
    val socialMedias: List<SocialMedia>,
    val photos: List<String>
) {
    fun toAboutEntity(): AboutEntity {
        return AboutEntity(
            members = members,
            socialMedias = socialMedias,
            photos = photos
        )
    }
}