package com.jdagnogo.welovemarathon.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapContent(
    state: MapState,
    onBackPressed: () -> Unit,
) {
    val singapore = LatLng(38.143414, 23.9830504)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                position = singapore,
                title = "Singapore",
                snippet = "Marker in Singapore"
            )
        }
        MapHeaderComponent(
            mapChips = state.chips,
            currentSelected = state.currentSelected,
            onBackPressed = onBackPressed, modifier = Modifier
        )
    }
}
