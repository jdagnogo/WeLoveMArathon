package com.jdagnogo.welovemarathon.restaurant.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.common.domain.EntityStringList
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantFilter

@Keep
@Entity(tableName = RestaurantFilterEntity.TABLE)
data class RestaurantFilterEntity(
    @PrimaryKey val id: String = "",
    val typeOfFilters: IconNameFilterList,
    val services: IconNameFilterList,
    val cuisines : EntityStringList,
    val plates : EntityStringList,
    val drinks : EntityStringList,
    val location : EntityStringList,
    val prices : EntityStringList,
) {
    fun toRestaurantFilter(): RestaurantFilter {
        return RestaurantFilter(
            typeOfFilters = typeOfFilters.iconNameFilter,
            services = services.iconNameFilter,
            cuisines = cuisines.data,
            plates = plates.data,
            drinks = drinks.data,
            location = location.data,
            prices = prices.data

        )
    }

    companion object {
        const val TABLE = "RestaurantFilterEntity"
    }
}