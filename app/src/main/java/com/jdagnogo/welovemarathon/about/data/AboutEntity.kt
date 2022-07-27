package com.jdagnogo.welovemarathon.about.data

import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName
import com.jdagnogo.welovemarathon.about.domain.About
import com.jdagnogo.welovemarathon.about.domain.Member
import com.jdagnogo.welovemarathon.about.domain.SocialMedia

@Keep
@Entity(tableName = AboutEntity.TABLE)
class AboutEntity (
    @PrimaryKey val id : String,
    val members: List<Member>,
    val socialMedias: List<SocialMedia>,
    val photos: List<String>,
    val mail: String = "",
    val phone: String ="",
){

    fun toAbout(): About {
        return About(
            members = members,
            socialMedias = socialMedias,
            photos = photos,
            mail = mail,
            phone = phone
        )
    }

    companion object {
        const val TABLE = "About"
    }
}