package com.jdagnogo.welovemarathon.about.data

import androidx.annotation.Keep
import androidx.room.Entity
import com.jdagnogo.welovemarathon.about.domain.About
import com.jdagnogo.welovemarathon.about.domain.Member
import com.jdagnogo.welovemarathon.about.domain.SocialMedia

@Keep
@Entity(tableName = AboutEntity.TABLE)
class AboutEntity (
    val members: List<Member>,
    val socialMedias: List<SocialMedia>,
    val photos: List<String>
){

    fun toAbout(): About {
        return About(
            members = members,
            socialMedias = socialMedias,
            photos = photos
        )
    }

    companion object {
        const val TABLE = "About"
    }
}