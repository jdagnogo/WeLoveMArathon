package com.jdagnogo.welovemarathon.about.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.about.data.AboutEntity

@Keep
data class About(
    val mail: String = "",
    val phone: String = "",
    val members: List<Member> = emptyList(),
    val socialMedias: List<SocialMedia> = emptyList(),
    val photos: List<String> = emptyList()
) {
    fun toAboutEntity(): AboutEntity {
        return AboutEntity(
            id = "id",
            members = members,
            socialMedias = socialMedias,
            photos = photos,
            mail = mail,
            phone = phone,
        )
    }
}