package com.jdagnogo.welovemarathon.about.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.about.data.AboutEntity
import com.jdagnogo.welovemarathon.common.domain.EntityStringList

@Keep
data class About(
    val mail: String = "",
    val phone: String = "",
    val policy: String = "",
    val members: List<Member> = emptyList(),
    val socialMedias: List<SocialMedia> = emptyList(),
    val photos: List<String> = emptyList()
) {
    fun toAboutEntity(): AboutEntity {
        return AboutEntity(
            id = "id",
            members = Members(members),
            socialMedias = SocialMedias(socialMedias),
            photos = EntityStringList(photos),
            mail = mail,
            phone = phone,
            policy = policy,
        )
    }
}