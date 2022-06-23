package com.jdagnogo.welovemarathon.favorites.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jdagnogo.welovemarathon.common.ui.component.MainDestinations

@Composable
fun FavoritesScreen(
    viewModel: FavViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    FavContent(
        state = state,
        onClearAllClicked = {
            viewModel.dispatchEvent(FavViewModel.FavUiEvent.OnClearAllClicked)
        },
        onRedirectToHomeClicked = {
            navController.navigate(MainDestinations.Home.route)
        },
        modifier = modifier
    )
}
