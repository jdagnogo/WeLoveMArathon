package com.jdagnogo.welovemarathon.events.presentation

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

/**
 * On-device geocoding (Android's Geocoder, free - no Maps Platform billing,
 * distinct from the paid Geocoding REST API) so the event detail screen can
 * show a real map pin for a free-text address without calling any billed
 * Google service. The app already ships the Maps SDK for Android for the
 * existing map feature (also free/unmetered - only the JS/Static/Street View
 * APIs are billed), so this reuses infrastructure already in the project.
 *
 * Returns null if the address can't be resolved - some devices/regions lack
 * backend geocoding support, and that's fine: the caller just skips the map
 * preview and keeps the tap-to-open-Maps link as the fallback.
 */
@Suppress("DEPRECATION")
suspend fun geocodeAddress(context: Context, address: String): LatLng? = withContext(Dispatchers.IO) {
    if (!Geocoder.isPresent()) return@withContext null
    runCatching {
        Geocoder(context, Locale.getDefault())
            .getFromLocationName(address, 1)
            ?.firstOrNull()
            ?.let { LatLng(it.latitude, it.longitude) }
    }.getOrNull()
}
