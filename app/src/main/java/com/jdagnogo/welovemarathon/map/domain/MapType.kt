package com.jdagnogo.welovemarathon.map.domain

sealed class MapType(val screenName: String) {
    object Shopping : MapType("Shopping")
    object Food : MapType("Food & Drink")
}
