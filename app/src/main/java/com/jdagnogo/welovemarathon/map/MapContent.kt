package com.jdagnogo.welovemarathon.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.jdagnogo.welovemarathon.map.domain.MapItem
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun MapContent(
    state: MapState,
    onCategorySelected: (id: String) -> Unit = {},
    OnMarkerSelected: (mapItem: MapItem) -> Unit = {},
    onBackPressed: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(state.currentPosition, state.zoom)
    }
    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            state.items.filter {
                it.latLng.latitude != 0.0
                        && it.latLng.longitude != 0.0
            }.forEach { mapItem ->
                Marker(
                    position = mapItem.latLng,
                    title = mapItem.name,
                    snippet = "subtitle Jeff",
                    onClick = {
                        coroutineScope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        mapItem.latLng.latitude,
                                        mapItem.latLng.longitude
                                    ), 16f
                                )
                            )
                        }
                        false
                    }
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
