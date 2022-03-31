package com.jdagnogo.welovemarathon.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

@ExperimentalMaterialApi
@Composable
fun MapContent(
    state: MapState,
    onCategorySelected: (id: String) -> Unit = {},
    onBackPressed: () -> Unit,
) {
    val startingPoint = remember { LatLng(38.143414, 23.9830504) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startingPoint, 10f)
    }
    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            state.items.filter {
                it.latLng.latitude != 0.0
                        && it.latLng.longitude != 0.0
            }
                .forEach {
                    Marker(
                        position = it.latLng!!,
                        title = it.name,
                        snippet = "subtitle Jeff"
                    )
                }
        }
        MapHeaderComponent(
            mapChips = state.chips,
            screenName = state.screenName,
            currentSelected = state.currentSelected,
            onChipClicked = onCategorySelected,
            onBackPressed = onBackPressed, modifier = Modifier
        )
    }
}
