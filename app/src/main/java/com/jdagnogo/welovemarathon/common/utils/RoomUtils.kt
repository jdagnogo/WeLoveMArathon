package com.jdagnogo.welovemarathon.common.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jdagnogo.welovemarathon.about.domain.Member
import com.jdagnogo.welovemarathon.about.domain.SocialMedia
import com.jdagnogo.welovemarathon.wine.domain.Wine

class Converters {
    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun socialMediasToJson(value: List<SocialMedia>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonTosocialMedias(value: String) =
        Gson().fromJson(value, Array<SocialMedia>::class.java).toList()

    @TypeConverter
    fun membersToJson(value: List<Member>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToMembers(value: String) = Gson().fromJson(value, Array<Member>::class.java).toList()

    @TypeConverter
    fun winesToJson(value: List<Wine>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToWines(value: String) = Gson().fromJson(value, Array<Wine>::class.java).toList()
}