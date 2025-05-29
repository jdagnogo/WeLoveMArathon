package com.jdagnogo.welovemarathon.map.domain

sealed class MapType(val key: String, val screenName: String) {
    object Shopping : MapType("Shopping", "Shopping")
    object Food : MapType("Food", "Food & Drink")
    object Activities : MapType("Activities", "Activities")
    object Beach : MapType("Beach", "Beaches")
    object Culture : MapType("Beach", "Beaches")
}
