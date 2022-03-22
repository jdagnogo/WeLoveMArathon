package com.jdagnogo.welovemarathon.map.domain

import androidx.annotation.Keep
import com.google.android.gms.maps.model.LatLng

@Keep
data class MapItem(
    val name: String = "",
    val image: String = "",
    val tags: String = "",
    val latLng: LatLng,
)
