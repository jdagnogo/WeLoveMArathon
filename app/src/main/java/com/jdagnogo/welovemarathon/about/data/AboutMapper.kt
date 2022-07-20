package com.jdagnogo.welovemarathon.about.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.about.domain.About

@Keep
class AboutMapper {
    fun toAboutEntity(about: About): AboutEntity {
        return about.toAboutEntity()
    }

    fun toAbout(entity: AboutEntity): About {
        return entity.toAbout()
    }
}