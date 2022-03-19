package com.jdagnogo.welovemarathon.map

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.R
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun MapScreen(
    viewModel: MapViewModel,
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()
    if (state.items.isEmpty()) {
        viewModel.dispatchEvent(MapUiEvent.OnInit(MapType.Shopping))
    }
    MapScaffoldComponent(
        content = {
            MapContent(
                state = state,
                onCategorySelected = {
                    viewModel.dispatchEvent(MapUiEvent.OnCategorySelected(it))
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
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetContent = sheetContent,
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // show snackbar as a suspend function
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Added to favorite ")
                    }
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_back),
                    contentDescription = "Localized description"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        sheetPeekHeight = 128.dp,
    ) { innerPadding ->
        content(innerPadding)
    }
}
