package com.jdagnogo.welovemarathon.restaurant.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.restaurant.domain.Amenities
@Keep
data class AmenitiesList(
    var amenities: List<Amenities>
)