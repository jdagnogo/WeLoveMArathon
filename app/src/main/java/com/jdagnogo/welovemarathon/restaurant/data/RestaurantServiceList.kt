package com.jdagnogo.welovemarathon.restaurant.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.restaurant.domain.RestaurantService
@Keep
data class RestaurantServiceList(
    var restaurantService: List<RestaurantService>
)