package com.jdagnogo.welovemarathon.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController

@Composable
fun MapScreen(
    viewModel: MapViewModel,
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()
    MapContent(
        state = state,
    onBackPressed = {
        navController.popBackStack()
    })
}
