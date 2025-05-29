package com.jdagnogo.welovemarathon.restaurant.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.restaurant.domain.Plates

@Keep
data class PlatesList(
    var plates: List<Plates>
)