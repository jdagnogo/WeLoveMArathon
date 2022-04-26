package com.jdagnogo.welovemarathon.map.presentation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.map.viewmodel.MapUiEvent
import com.jdagnogo.welovemarathon.map.viewmodel.MapViewModel

@ExperimentalMaterialApi
@Composable
fun MapScreen(
    mapType: String,
    viewModel: MapViewModel,
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()
    viewModel.dispatchEvent(MapUiEvent.OnInit(mapType))
    MapScaffoldComponent(
        content = {
            MapContent(
                state = state,
                onCategorySelected = {
                    viewModel.dispatchEvent(MapUiEvent.OnCategorySelected(it))
                },
                OnMarkerSelected = {
                    viewModel.dispatchEvent(MapUiEvent.OnMarkerSelected(it))
                },
                onBackPressed = { navController.popBackStack() })
        },
        sheetContent = {
            MapBottomSheetComponent(
                mapItems = state.items,
                Modifier.fillMaxWidth()
            )
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun MapScaffoldComponent(
    sheetContent: @Composable ColumnScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {}
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetContent = sheetContent,
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
        floatingActionButtonPosition = FabPosition.End,
        sheetPeekHeight = 128.dp,
    ) { innerPadding ->
        content(innerPadding)
    }
}
