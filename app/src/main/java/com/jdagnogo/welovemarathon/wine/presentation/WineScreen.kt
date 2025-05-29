package com.jdagnogo.welovemarathon.wine.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun WineScreen(
    viewModel: WineViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    val state by viewModel.state.collectAsState()
    WineContent(
        state = state,
        onBackPressed = {
            navController.popBackStack()
        },
        onMapSelected = {
                        //open google
        },
        modifier = modifier
    )
}
