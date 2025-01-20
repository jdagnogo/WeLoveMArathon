package com.jdagnogo.welovemarathon.restaurant.domain

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.common.domain.EntityStringList
import com.jdagnogo.welovemarathon.restaurant.data.IconNameFilterList
import com.jdagnogo.welovemarathon.restaurant.data.RestaurantFilterEntity

@Keep
data class RestaurantFilter(
    val typeOfFilters: List<IconNameFilter> = listOf(),
    val services: List<IconNameFilter> = listOf(),
    val cuisines : List<String> = listOf(),
    val plates : List<String> = listOf(),
    val drinks : List<String> = listOf(),
    val location : List<String> = listOf(),
    val prices : List<String> = listOf(),
    val handicapAccess : Boolean = false,
    val evCharger : Boolean = false,
) {
    fun toRestaurantFilterEntity() : RestaurantFilterEntity{
        return RestaurantFilterEntity(
            typeOfFilters = IconNameFilterList(typeOfFilters),
            services = IconNameFilterList(services),
            cuisines = EntityStringList(cuisines),
            plates = EntityStringList(plates),
            drinks = EntityStringList(drinks),
            location = EntityStringList(location),
            prices = EntityStringList(prices),
            handicapAccess = handicapAccess,
            evCharger = evCharger,
        )
    }
}
@Keep
data class IconNameFilter(
    val name : String = "",
    val icon : String = "",
    val ordinal : Int = 0,
)