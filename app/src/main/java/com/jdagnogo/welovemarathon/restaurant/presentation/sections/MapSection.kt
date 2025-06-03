package com.jdagnogo.welovemarathon.restaurant.presentation.sections

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.jdagnogo.welovemarathon.common.ui.theme.Secondary
import com.jdagnogo.welovemarathon.common.ui.theme.Shapes

fun LazyListScope.mapSection(
    modifier: Modifier = Modifier,
    name: String,
    coordinate: GeoPoint? = null,
) {
    if (coordinate == null) return
    item("mapSection") {
        val latLng = remember {
            LatLng(coordinate.latitude, coordinate.longitude)
        }
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(latLng, 17f)
        }
        GoogleMap(
            modifier = modifier
                .padding(16.dp)
                .clip(Shapes.medium)
                .border(width = 1.dp, color = Secondary, shape = Shapes.medium),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                position = latLng,
                title = name,
                onClick = { false }
            )
        }
    }
}