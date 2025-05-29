package com.jdagnogo.welovemarathon.common.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jdagnogo.welovemarathon.about.domain.Members
import com.jdagnogo.welovemarathon.about.domain.SocialMedias
import com.jdagnogo.welovemarathon.common.domain.EntityStringList
import com.jdagnogo.welovemarathon.offer.data.Promos
import com.jdagnogo.welovemarathon.restaurant.data.AmenitiesList
import com.jdagnogo.welovemarathon.restaurant.data.IconNameFilterList
import com.jdagnogo.welovemarathon.restaurant.data.PlatesList
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantServiceList
import com.jdagnogo.welovemarathon.wine.domain.Wines

class Converters {
    @TypeConverter
    fun convertImageListToJSONString(invoiceList: EntityStringList): String = Gson().toJson(invoiceList)

    @TypeConverter
    fun convertJSONStringToImageList(jsonString: String): EntityStringList =
        Gson().fromJson(jsonString, EntityStringList::class.java)

    @TypeConverter
    fun winesToString(wines: Wines): String = Gson().toJson(wines)

    @TypeConverter
    fun stringToWines(jsonString: String): Wines =
        Gson().fromJson(jsonString, Wines::class.java)

    @TypeConverter
    fun amenitiesToString(amenities: AmenitiesList): String = Gson().toJson(amenities)

    @TypeConverter
    fun stringToAmenties(jsonString: String): AmenitiesList =
        Gson().fromJson(jsonString, AmenitiesList::class.java)

    @TypeConverter
    fun iconNameFilterListToString(data: IconNameFilterList): String = Gson().toJson(data)

    @TypeConverter
    fun stringToIconNameFilterList(jsonString: String): IconNameFilterList =
        Gson().fromJson(jsonString, IconNameFilterList::class.java)

    @TypeConverter
    fun platesToString(plates: PlatesList): String = Gson().toJson(plates)

    @TypeConverter
    fun stringToPlates(jsonString: String): PlatesList =
        Gson().fromJson(jsonString, PlatesList::class.java)

    @TypeConverter
    fun restaurantServiceToString(data: RestaurantServiceList): String = Gson().toJson(data)

    @TypeConverter
    fun stringToRestaurantService(jsonString: String): RestaurantServiceList =
        Gson().fromJson(jsonString, RestaurantServiceList::class.java)

    @TypeConverter
    fun socialMediaToString(data: SocialMedias): String = Gson().toJson(data)

    @TypeConverter
    fun stringToSocialMedia(data: String): SocialMedias =
        Gson().fromJson(data, SocialMedias::class.java)

    @TypeConverter
    fun promosToString(data: Promos): String = Gson().toJson(data)

    @TypeConverter
    fun stringToPromos(data: String): Promos =
        Gson().fromJson(data, Promos::class.java)

    @TypeConverter
    fun memberToString(data: Members): String = Gson().toJson(data)

    @TypeConverter
    fun stringToSocialMember(data: String): Members =
        Gson().fromJson(data, Members::class.java)
}