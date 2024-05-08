package com.jdagnogo.welovemarathon.common.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jdagnogo.welovemarathon.about.domain.Member
import com.jdagnogo.welovemarathon.about.domain.Members
import com.jdagnogo.welovemarathon.about.domain.SocialMedias
import com.jdagnogo.welovemarathon.common.domain.ImageList
import com.jdagnogo.welovemarathon.wine.domain.Wines

class Converters {
    @TypeConverter
    fun convertImageListToJSONString(invoiceList: ImageList): String = Gson().toJson(invoiceList)

    @TypeConverter
    fun convertJSONStringToImageList(jsonString: String): ImageList =
        Gson().fromJson(jsonString, ImageList::class.java)

    @TypeConverter
    fun winesToString(wines: Wines): String = Gson().toJson(wines)

    @TypeConverter
    fun stringToWines(jsonString: String): Wines =
        Gson().fromJson(jsonString, Wines::class.java)

    @TypeConverter
    fun socialMediaToString(data: SocialMedias): String = Gson().toJson(data)

    @TypeConverter
    fun stringToSocialMedia(data: String): SocialMedias =
        Gson().fromJson(data, SocialMedias::class.java)

    @TypeConverter
    fun memberToString(data: Members): String = Gson().toJson(data)

    @TypeConverter
    fun stringToSocialMember(data: String): Members =
        Gson().fromJson(data, Members::class.java)
}