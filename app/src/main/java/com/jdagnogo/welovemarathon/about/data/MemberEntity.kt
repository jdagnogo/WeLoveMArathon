package com.jdagnogo.welovemarathon.about.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.about.domain.Member

@Keep
@Entity(tableName = MemberEntity.TABLE)
data class MemberEntity(
    @PrimaryKey val name: String,
    val icon: String,
    val ordinal: Int = 0,
) {

    fun toMember(): Member {
        return Member(
            name = name,
            icon = icon,
            ordinal = ordinal
        )
    }

    companion object {
        const val TABLE = "Member"
    }
}