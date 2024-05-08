package com.jdagnogo.welovemarathon.about.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.about.domain.About
import com.jdagnogo.welovemarathon.about.domain.Member
import com.jdagnogo.welovemarathon.about.domain.Members
import com.jdagnogo.welovemarathon.about.domain.SocialMedias
import com.jdagnogo.welovemarathon.common.domain.ImageList

@Keep
@Entity(tableName = AboutEntity.TABLE)
class AboutEntity(
    @PrimaryKey val id: String,
    val members: Members,
    val socialMedias: SocialMedias,
    var photos: ImageList,
    val mail: String = "",
    val phone: String = "",
    val policy: String = "",
) {

    fun toAbout(): About {
        return About(
            members = members.member,
            socialMedias = socialMedias.socialMedia,
            photos = photos.images,
            mail = mail,
            phone = phone,
            policy = policy,
        )
    }

    companion object {
        const val TABLE = "About"
    }
}