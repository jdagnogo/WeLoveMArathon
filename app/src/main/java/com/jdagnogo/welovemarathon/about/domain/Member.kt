package com.jdagnogo.welovemarathon.about.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.about.data.MemberEntity

@Keep
data class Member(
    val name: String,
    val icon: String,
    val ordinal: Int = 0,
) {
    fun toMemberEntity(): MemberEntity {
        return MemberEntity(
            name = name,
            icon = icon,
            ordinal = ordinal
        )
    }
}