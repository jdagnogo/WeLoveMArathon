package com.jdagnogo.welovemarathon.restaurant.domain

import androidx.annotation.Keep
import com.google.firebase.firestore.GeoPoint
@Keep
data class Restaurant(
    val id: String = "",
    val name: String = "",
    val website: String = "",
    val location: String = "",
    val locationLink: String = "",
    var coordinate: GeoPoint? = null,
    val number: String = "",
    val description: String = "",
    val isRecommended: Boolean = false,
    val services: List<RestaurantService> = emptyList(),
    val plates: List<Plates> = emptyList(),
    val amenities: List<Amenities> = emptyList(),
    val images: List<String> = emptyList(),
    val bigImages: List<String> = emptyList(),
)